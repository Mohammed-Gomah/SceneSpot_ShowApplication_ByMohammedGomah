package com.example.scenespotnersion2.main.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import androidx.fragment.app.Fragment
import com.example.scenespotnersion2.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initWebView()
    }

    private fun initWebView() {
        with(binding.wvDetailsTrailer.settings) {
            javaScriptEnabled = true
            domStorageEnabled = true
            mediaPlaybackRequiresUserGesture = false // لتشغيل الفيديو تلقائيًا بدون تفاعل المستخدم
        }

        binding.wvDetailsTrailer.webChromeClient = object : WebChromeClient() {
            override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
                super.onShowCustomView(view, callback)
                // يمكنك إضافة كود هنا لدعم ملء الشاشة إذا احتجت ذلك
            }
        }
        val videoId = "L_CV2tgpz68" // استبدل بـ ID الفيديو المطلوب
        val embedHtml = """
vDetailsTrailer.loadData(embedHtml, "text/html", "utf-8")
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}