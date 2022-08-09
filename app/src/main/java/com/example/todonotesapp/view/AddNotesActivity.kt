package com.example.todonotesapp.view

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.media.Ringtone
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.example.todonotesapp.BuildConfig
import com.example.todonotesapp.R
import com.example.todonotesapp.utils.AppConstants
import java.io.File
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest
import kotlin.collections.ArrayList

class AddNotesActivity : AppCompatActivity() {

    lateinit var editTextTitle: EditText
    lateinit var editTextDescription:EditText
    lateinit var submitButton: Button
    lateinit var imageViewAdd: ImageView
    val REQUEST_CODE_GALLERY = 2
    val REQUEST_CODE_CAMERA = 1
    val MY_PERMISSION_CODE = 124
    var picturePath = ""
    lateinit var imageLocation: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)

        bindViews()
        clickListener()
    }

    private fun clickListener() {
        submitButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val intent = Intent()
                intent.putExtra(AppConstants.TITLE, editTextTitle.text.toString())
                intent.putExtra(AppConstants.DESCRIPTION, editTextDescription.text.toString())
                intent.putExtra(AppConstants.IMAGE_PATH,picturePath)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }

        })

        imageViewAdd.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                if (checkAndRequestPermission()) {
                    
                }
                setupDialog()
            }

        })
    }

    private fun checkAndRequestPermission(): Boolean {

        val permissionCamera = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
        val permissionStorage = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
        val listPermissionNeeded = ArrayList<String>()

        if (permissionStorage != PackageManager.PERMISSION_GRANTED){
            listPermissionNeeded.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (permissionCamera != PackageManager.PERMISSION_GRANTED){
            listPermissionNeeded.add(android.Manifest.permission.CAMERA)
        }
        if (listPermissionNeeded.isNotEmpty()){
            ActivityCompat.requestPermissions(this, listPermissionNeeded.toTypedArray<String>(), MY_PERMISSION_CODE)
            return false
        }
        return true

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            MY_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty()&& grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    setupDialog()
                }
            }
        }
    }

    private fun setupDialog() {
        val view = LayoutInflater.from(this@AddNotesActivity).inflate(R.layout.dialog_selector, null)
        val textViewCamera = view.findViewById<TextView>(R.id.textViewCamera)
        val textViewGallery = view.findViewById<TextView>(R.id.textViewGallery)

        val dialog = AlertDialog.Builder(this).setView(view).setCancelable(true).create()

        textViewGallery.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, REQUEST_CODE_GALLERY)
                dialog.hide()
            }

        })

        textViewCamera.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (takePictureIntent.resolveActivity(packageManager) != null){
                    var photoFile:File? = null
                    try {
                        photoFile = createImageFile()
                    } catch (e: Exception){

                    }

                    if (photoFile != null){
                        val photoUri: Uri = FileProvider.getUriForFile(this@AddNotesActivity,
                        BuildConfig.APPLICATION_ID + ".provider", photoFile)
                        imageLocation = photoFile
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                        dialog.hide()
                        startActivityForResult(takePictureIntent,REQUEST_CODE_CAMERA)
                    }

                }
            }

        })


        dialog.show()
    }

    private fun createImageFile(): File? {
        val timeStamp = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
        val fileName = "JPEG" + timeStamp + "_"
        val storageDir : File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName,".jpg",storageDir)

    }

    private fun bindViews() {
        editTextTitle = findViewById(R.id.editTextTitle)
        editTextDescription = findViewById(R.id.editTextDescription)
        submitButton = findViewById(R.id.button_submit)
        imageViewAdd = findViewById(R.id.imageViewAdd)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when(requestCode){
                REQUEST_CODE_CAMERA -> {
                    picturePath = imageLocation.path.toString()
                    Glide.with(this).load(imageLocation.absoluteFile).into(imageViewAdd)
                }
                REQUEST_CODE_GALLERY -> {
                    val selectedImage = data?.data
                    picturePath = selectedImage.toString()
                    Glide.with(this).load(picturePath).into(imageViewAdd)
                }
            }
        }
    }
}