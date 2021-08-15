package com.fire.pos.presentation.productdetail

import android.Manifest
import android.annotation.TargetApi
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.fire.pos.R
import com.fire.pos.base.fragment.BaseFragment
import com.fire.pos.databinding.FragmentProductDetailBinding
import com.fire.pos.di.appComponent
import com.fire.pos.presentation.loadingdialog.LoadingDialogFragment
import com.fire.pos.presentation.productdetail.di.DaggerProductDetailComponent
import com.fire.pos.presentation.productdetail.viewmodel.ProductDetailViewModel
import com.fire.pos.presentation.productdetail.viewmodel.ProductDetailViewModelContract
import com.fire.pos.util.*
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.destination
import pl.aprilapps.easyphotopicker.ChooserType
import pl.aprilapps.easyphotopicker.EasyImage
import pl.aprilapps.easyphotopicker.MediaFile
import pl.aprilapps.easyphotopicker.MediaSource
import java.io.File
import javax.inject.Inject

/**
 * Created by Chandra.
 **/

class ProductDetailFragment :
    BaseFragment<ProductDetailViewModel, ProductDetailViewModelContract, FragmentProductDetailBinding>(),
    ProductDetailView {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    private lateinit var easyImage: EasyImage

    private var loadingDialog: LoadingDialogFragment? = null

    private var currentRequest: String = Manifest.permission.CAMERA

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                when (currentRequest) {
                    Manifest.permission.CAMERA -> requestReadStoragePermission()
                    Manifest.permission.READ_EXTERNAL_STORAGE -> requestWriteStoragePermission()
                    Manifest.permission.WRITE_EXTERNAL_STORAGE -> showImageSourceOptionDialog()
                }
            } else {
                when (currentRequest) {
                    Manifest.permission.CAMERA -> {
                        context?.showMessageDialog(
                            "This feature needs to access your camera to take a picture of your product."
                        )
                    }
                    Manifest.permission.READ_EXTERNAL_STORAGE -> {
                        context?.showMessageDialog(
                            "This feature needs to access your storage to choose an image your product."
                        )
                    }
                    Manifest.permission.WRITE_EXTERNAL_STORAGE -> {
                        context?.showMessageDialog(
                            "This feature needs to access your storage to choose an image your product."
                        )
                    }
                }
            }
        }

    override fun getLayoutId(): Int {
        return R.layout.fragment_product_detail
    }

    override fun getViewModelClass(): Class<ProductDetailViewModel> {
        return ProductDetailViewModel::class.java
    }

    override fun getViewModelFactory(): ViewModelProvider.Factory {
        return viewModelProviderFactory
    }

    override fun setupDependencyInjection() {
        DaggerProductDetailComponent.builder()
            .appComponent(appComponent())
            .build()
            .inject(this)
    }

    override fun subscribeLiveData() {
        viewModel.compressedImage.observe(this, {
            Glide.with(this).load(it).into(binding.imgProduct)
        })

        viewModel.isLoading.observe(this, {
            if (it) loadingDialog?.show(childFragmentManager, LoadingDialogFragment.TAG)
            else loadingDialog?.dismiss()
        })

        viewModel.productError.observe(this, {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })

        viewModel.addProductSuccess.observe(this, {
            Toast.makeText(context, "SUCCESS", Toast.LENGTH_SHORT).show()
        })
    }

    override fun initView() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        arguments?.let {
            val isEditMode = ProductDetailFragmentArgs.fromBundle(it).isEditMode
            binding.containerActionAdd.visible(!isEditMode)
            binding.containerActionUpdate.visible(isEditMode)
        }

        binding.btnAddImage.setOnClickListener {
            requestCameraPermission()
        }
        binding.btnAddProduct.setOnClickListener {
            addProduct()
        }
        binding.btnUpdate.setOnClickListener { }
        binding.btnDelete.setOnClickListener { }

        loadingDialog = LoadingDialogFragment.instance()
    }

    override fun requestCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                requestReadStoragePermission()
            }

            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {

            }

            else -> {
                currentRequest = Manifest.permission.CAMERA
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    override fun requestReadStoragePermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                requestWriteStoragePermission()
            }

            shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) -> {

            }

            else -> {
                currentRequest = Manifest.permission.READ_EXTERNAL_STORAGE
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }

    override fun requestWriteStoragePermission() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            showImageSourceOptionDialog()
            return
        }

        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                showImageSourceOptionDialog()
            }

            shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE) -> {

            }

            else -> {
                currentRequest = Manifest.permission.WRITE_EXTERNAL_STORAGE
                requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
    }

    override fun showImageSourceOptionDialog() {
        val options = arrayOf("Camera", "Gallery")
        AlertDialog.Builder(requireContext())
            .setTitle("Choose image source")
            .setItems(options) { _, position ->
                if (position == 0) easyImage.openCameraForImage(this)
                else easyImage.openGallery(this)
            }
            .show()
    }

    override fun addProduct() {
        val name = binding.edtName.text.toString()

        val price =
            if (binding.edtPrice.text?.toString().isNullOrBlank()) 0L
            else binding.edtPrice.text.toString().toLong()

        val stock =
            if (binding.edtStock.text?.toString().isNullOrBlank()) 0L
            else binding.edtStock.text.toString().toLong()

        viewModel.addProduct(name, price, stock)
    }

    override fun deleteProduct() {

    }

    override fun updateProduct() {

    }

    override fun initEasyImage() {
        easyImage = EasyImage.Builder(requireContext())
            .allowMultiple(false)
            .setChooserTitle("Choose Image")
            .setChooserType(ChooserType.CAMERA_AND_GALLERY)
            .build()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initEasyImage()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        easyImage.handleActivityResult(
            requestCode,
            resultCode,
            data,
            requireActivity(),
            object : EasyImage.Callbacks {
                override fun onCanceled(source: MediaSource) = Unit

                override fun onImagePickerError(error: Throwable, source: MediaSource) {
                    error.printStackTrace()
                    Toast.makeText(
                        this@ProductDetailFragment.context,
                        error.message ?: "Failed to pick an image",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onMediaFilesPicked(imageFiles: Array<MediaFile>, source: MediaSource) {
                    viewModel.compressImage(requireContext(), imageFiles[0].file)
                }

            })
    }


}