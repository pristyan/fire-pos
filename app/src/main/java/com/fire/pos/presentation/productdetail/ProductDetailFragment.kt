package com.fire.pos.presentation.productdetail

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.fire.pos.R
import com.fire.pos.base.fragment.BaseFragment
import com.fire.pos.constant.AppConstant
import com.fire.pos.model.view.Product
import com.fire.pos.databinding.FragmentProductDetailBinding
import com.fire.pos.di.appComponent
import com.fire.pos.presentation.loadingdialog.LoadingDialogFragment
import com.fire.pos.presentation.productdetail.di.DaggerProductDetailComponent
import com.fire.pos.presentation.productdetail.viewmodel.ProductDetailViewModel
import com.fire.pos.presentation.productdetail.viewmodel.ProductDetailViewModelContract
import com.fire.pos.util.*
import pl.aprilapps.easyphotopicker.ChooserType
import pl.aprilapps.easyphotopicker.EasyImage
import pl.aprilapps.easyphotopicker.MediaFile
import pl.aprilapps.easyphotopicker.MediaSource
import javax.inject.Inject

/**
 * Created by Chandra.
 **/

class ProductDetailFragment :
    BaseFragment<ProductDetailViewModel, ProductDetailViewModelContract, FragmentProductDetailBinding>(),
    ProductDetailView {

    @Inject
    override lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    private lateinit var easyImage: EasyImage

    private var loadingDialog: LoadingDialogFragment? = null

    private var currentProduct: Product? = null

    private var currentPermission = Manifest.permission.CAMERA

    private var permissionRequestLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            when (currentPermission) {
                Manifest.permission.CAMERA -> {
                    requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
                Manifest.permission.READ_EXTERNAL_STORAGE -> {
                    showImageSourceOptionDialog()
                }
            }
        } else {
            when (currentPermission) {
                Manifest.permission.CAMERA -> {
                    context?.showMessageDialog(getString(R.string.msg_camera_rationale))
                }
                Manifest.permission.READ_EXTERNAL_STORAGE -> {
                    context?.showMessageDialog(getString(R.string.msg_storage_rationale))
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
            context?.toast(it)
        })

        viewModel.addProductSuccess.observe(this, {
            context?.toast("Product added successfully")
            setBackStackState(true)
            findNavController().popBackStack()
        })

        viewModel.updateProductSuccess.observe(this, {
            context?.toast("Product updated successfully")
            setBackStackState(true)
            findNavController().popBackStack()
        })

        viewModel.deleteProductSuccess.observe(this, {
            context?.toast("Product deleted successfully")
            setBackStackState(true)
            findNavController().popBackStack()
        })
    }

    override fun initView() {
        binding.toolbar.setupNavigationBack { setBackStackState(true) }

        arguments?.let {
            val isEditMode = ProductDetailFragmentArgs.fromBundle(it).isEditMode
            binding.containerActionAdd.visible(!isEditMode)
            binding.containerActionUpdate.visible(isEditMode)
            currentProduct = ProductDetailFragmentArgs.fromBundle(it).product
            currentProduct?.let { product ->
                binding.product = product
                Glide.with(this)
                    .load(product.image)
                    .into(binding.imgProduct)
            }

            if (currentProduct == null) {
                Glide.with(this)
                    .load(R.drawable.img_default_product)
                    .into(binding.imgProduct)
            }
        }

        binding.btnAddImage.setOnClickListener { requestPermission(Manifest.permission.CAMERA) }
        binding.btnAddProduct.setOnClickListener { addProduct() }
        binding.btnUpdate.setOnClickListener {
            context?.showConfirmationDialog(
                R.string.dialog_title_confirmation,
                R.string.dialog_msg_update_product
            ) {
                updateProduct()
            }
        }
        binding.btnDelete.setOnClickListener {
            context?.showConfirmationDialog(
                R.string.dialog_title_confirmation,
                R.string.dialog_msg_delete_product
            ) {
                deleteProduct()
            }
        }

        loadingDialog = LoadingDialogFragment.instance()
    }

    override fun requestPermission(permission: String) {
        val isGranted = ContextCompat.checkSelfPermission(
            requireContext(), permission
        ) == PackageManager.PERMISSION_GRANTED

        when {
            isGranted && permission == Manifest.permission.CAMERA -> {
                requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            }

            isGranted && permission == Manifest.permission.READ_EXTERNAL_STORAGE -> {
                showImageSourceOptionDialog()
            }

            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                context?.showMessageDialog(getString(R.string.msg_camera_rationale))
            }

            shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                context?.showMessageDialog(getString(R.string.msg_storage_rationale))
            }

            else -> {
                currentPermission = permission
                permissionRequestLauncher.launch(permission)
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
        currentProduct?.let {
            viewModel.deleteProduct(it)
        }
    }

    override fun updateProduct() {
        currentProduct?.let {
            val name = binding.edtName.text.toString()

            val price =
                if (binding.edtPrice.text?.toString().isNullOrBlank()) 0L
                else binding.edtPrice.text.toString().toLong()

            val stock =
                if (binding.edtStock.text?.toString().isNullOrBlank()) 0L
                else binding.edtStock.text.toString().toLong()

            val request = it.copy(name = name, price = price, stock = stock)

            viewModel.updateProduct(request)
        }
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
                    context?.toast(error.message ?: "Failed to pick an image")
                }

                override fun onMediaFilesPicked(imageFiles: Array<MediaFile>, source: MediaSource) {
                    viewModel.compressImage(requireContext(), imageFiles[0].file)
                }

            })
    }

    override fun setBackStackState(needRefresh: Boolean) {
        findNavController().previousBackStackEntry
            ?.savedStateHandle
            ?.set(AppConstant.NEED_REFRESH, needRefresh)
    }

}