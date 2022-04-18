package com.example.healthapp.ui.information

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hadilq.liveevent.LiveEvent

class InformationViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    val onVideoCardClickEvent = LiveEvent<Unit>()
    val onBlogCardClickEvent = LiveEvent<Unit>()


    fun videoCardClicked(){
        onVideoCardClickEvent.value = Unit
    }

    fun blogCardClicked(){
        onBlogCardClickEvent.value = Unit
    }
}

//You can add blogs realted to health and videos, whatever gets implemented
//
//The primary color can be accesed using @color/primary
//Try to keep the ui sober and the code clean