package com.christhemar.carritorest.fragments

import android.Manifest
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.christhemar.carritorest.R
import com.christhemar.carritorest.interfaces.Navegar
import com.google.android.material.button.MaterialButton

class LoginFragment : Fragment() {

    lateinit var imagenPerfil: ImageView
    lateinit var editImage: ImageButton
    lateinit var omitir: MaterialButton
    lateinit var txtNombre_login: TextView
    var progressDialog: ProgressDialog? = null
    private val CAMERA_REQUEST = 100
    private val IMAGE_PICK_CAMERA_REQUEST = 400

    var permisosCamara: Array<String> = emptyArray()
    var imagenUri: Uri? = null
    var perfilCoverImage: String = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btn = view.findViewById<Button>(R.id.btnIr)
        imagenPerfil = view.findViewById(R.id.imgProfile)
        editImage = view.findViewById(R.id.editImage)
        omitir = view.findViewById(R.id.btnOmitir)
        txtNombre_login = view.findViewById(R.id.txtnombreUser)

        progressDialog = ProgressDialog(context)
        progressDialog?.setCanceledOnTouchOutside(false)
        permisosCamara = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        editImage.setOnClickListener {
            progressDialog?.setMessage("Actualizar Perfil")
            perfilCoverImage = "image"
            showImagePicDialog()

        }

        btn.setOnClickListener(View.OnClickListener() {
            //Redireccionar
            if (imagenUri != null && !txtNombre_login.text.toString().trim().equals("")) {
                val bundle = Bundle()
                bundle.putString("imgUri", imagenUri?.toString())
                bundle.putString("nombreUser",txtNombre_login.text.toString())
                parentFragmentManager.setFragmentResult("foto", bundle)
                (activity as Navegar).navegar(mainFragment(), false)
            } else {
                Toast.makeText(context, "Foto de perfil y credenciales obligatoria", Toast.LENGTH_LONG).show()
            }

        })

        omitir.setOnClickListener {
            (activity as Navegar).navegar(mainFragment(), true)
        }


    }

    override fun onPause() {
        super.onPause()
        Glide.with(this).load(imagenUri).into(imagenPerfil)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            CAMERA_REQUEST -> {
                if (grantResults.size > 0) {
                    val cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                    val writeStorageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED
                    if (cameraAccepted && writeStorageAccepted) {
                        pickFromCamera()
                    } else {
                        Toast.makeText(
                            context,
                            "Habilite los permisos de camara y almacenamiento!!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Toast.makeText(context, "Intente de Nuevo", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    private fun showImagePicDialog() {
        var opciones:Array<String> = arrayOf("Camera", "Gallery")
        val builder=AlertDialog.Builder(context)
        builder.setTitle("Elegir una imagen..")
        builder.setItems(opciones, DialogInterface.OnClickListener { dialogInterface, i ->
            if (i == 0) {
                if (!checkCameraPermission()) {
                    requestCameraPermission()
                } else {
                    pickFromCamera()
                }
            }
        })
        builder.create().show()
    }

    private fun pickFromCamera() {
        val contentValues=ContentValues()
        contentValues.put(MediaStore.Images.Media.TITLE, "Temp_pic")
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "Temp description")
        imagenUri= context?.contentResolver?.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )
        val cameraIntent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imagenUri)
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_REQUEST)
    }

    private fun requestCameraPermission() {
        requestPermissions(permisosCamara, CAMERA_REQUEST)
    }

    private fun checkCameraPermission():Boolean{
        val result1:Boolean=ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        )==(PackageManager.PERMISSION_DENIED)
        val result2:Boolean=ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )==(PackageManager.PERMISSION_GRANTED)
        return result1&&result2
    }


}