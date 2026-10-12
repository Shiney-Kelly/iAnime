package com.project.ianime.feature.manageanime

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import com.google.android.material.button.MaterialButton
import com.project.ianime.R
import com.project.ianime.databinding.FragmentManageAnimeBinding
import com.project.ianime.core.ui.root.BaseFragment
import com.project.ianime.utils.image.ImageUtils
import com.project.ianime.utils.showExitBeforeDialog
import com.project.ianime.core.ui.widget.AnimeEditText
import com.project.ianime.core.ui.widget.setShowProgress
import io.reactivex.rxjava3.core.Observable
import java.io.IOException

abstract class ManageAnimeFragment : BaseFragment() {

    private val imageUtils = ImageUtils()
    private lateinit var selectedImageBitmap: Bitmap

    private var _binding: FragmentManageAnimeBinding? = null
    private val binding get() = _binding!!
    lateinit var toolbar: Toolbar
    lateinit var addProfileButton: Button
    lateinit var profilePreview: ImageView
    lateinit var animeChineseName: AnimeEditText
    lateinit var animeEnglishName: AnimeEditText
    lateinit var animeRate: AnimeEditText
    lateinit var animeYear: AnimeEditText
    lateinit var animeDescription: AnimeEditText
    lateinit var animeCountry: AnimeEditText
    lateinit var animeType: AnimeEditText
    lateinit var animeStatus: AnimeEditText
    lateinit var saveButton: MaterialButton
    var pictureSelectedState: Boolean = false
    var formEnteredState: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManageAnimeBinding.inflate(inflater, container, false)
        toolbar = binding.topAppBar.toolBar

        addProfileButton = binding.addCoverButton
        profilePreview = binding.profilePreview
        animeChineseName = binding.manageChineseName
        animeEnglishName = binding.manageEnglishName
        animeRate = binding.manageRate
        animeYear = binding.manageYear
        animeDescription = binding.manageDescription
        animeCountry = binding.manageCountry
        animeType = binding.manageType
        animeStatus = binding.manageStatus
        saveButton = binding.buttonSave

        Observable.combineLatest(
            listOf(
                animeChineseName.setTextChangeListener().startWithItem(animeChineseName.isValid()),
                animeEnglishName.setTextChangeListener().startWithItem(animeEnglishName.isValid()),
                animeRate.setTextChangeListener().startWithItem(animeRate.isValid()),
                animeYear.setTextChangeListener().startWithItem(animeYear.isValid()),
                animeDescription.setTextChangeListener().startWithItem(animeDescription.isValid()),
                animeCountry.setTextChangeListener().startWithItem(animeCountry.isValid()),
                animeType.setTextChangeListener().startWithItem(animeType.isValid()),
                animeStatus.setTextChangeListener().startWithItem(animeStatus.isValid())
            )
        ) { values ->
            values.forEach {
                if (!(it as Boolean)) {
                    return@combineLatest false
                }
            }
            return@combineLatest true
        }
            .distinctUntilChanged { _, current -> current == saveButton.isEnabled }
            .subscribe { isValid ->
                formEnteredState = isValid
                activateSaveButton()
            }
            .also { compositeDisposable.add(it) }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadInitData()
        addProfileButton.setOnClickListener {
            openGallery()
        }

        saveButton.setOnClickListener {
            startLoadingState()
//            saveAnime()
        }
    }

    private fun loadInitData() {
        animeChineseName.setInitialText("完美世界" ?: "")
        animeEnglishName.setInitialText("Perfect World" ?: "")
        animeRate.setInitialText("10.0" ?: "")
        animeYear.setInitialText("2021" ?: "")
        animeDescription.setInitialText("My favourite Anime" ?: "")
        animeCountry.dropdownOption = getString(R.string.test_anime_country)
        animeType.dropdownOption = "God"
        animeStatus.dropdownOption = "In Progress"
        saveButton.isEnabled = false
    }

    override fun navigateBack(): Boolean {
        return if (canNavigateBackState()) {
            appNavigation.closeTopFragment()
        } else {
            showExitBeforeDialog(requireContext(), appNavigation)
            true
        }
    }

    abstract fun saveAnime()

    private fun activateSaveButton() {
        saveButton.isEnabled = formEnteredState && pictureSelectedState
    }

    private fun canNavigateBackState(): Boolean {
        return !saveButton.isEnabled
    }

    private fun startLoadingState() {
        saveButton.setShowProgress(true)
    }

    private fun stopLoadingState() {
        saveButton.setShowProgress(false)
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        try {
            uploadProfileActivity.launch(intent)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }

    private val uploadProfileActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                if (data != null && data.data != null) {
                    val selectedImageUri: Uri? = data.data
                    selectedImageBitmap = MediaStore.Images.Media.getBitmap(
                        requireActivity().contentResolver,
                        selectedImageUri
                    )
                    try {
                        profilePreview.setImageURI(selectedImageUri)
                        pictureSelectedState = true
                        activateSaveButton()
                        // Used for debug purpose
                        Log.i(IMAGE_BMP, "Bitmap is: " + selectedImageBitmap)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val IMAGE_BMP = "image_bmp"
    }
}