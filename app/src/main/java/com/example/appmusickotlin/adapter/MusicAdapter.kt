package com.example.appmusickotlin.adapter

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import androidx.core.util.TimeUtils.formatDuration
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.appmusickotlin.R
import com.example.appmusickotlin.databinding.MusicItemLayoutBinding
import com.example.appmusickotlin.model.Song
import com.google.ai.client.generativeai.type.content

class MusicAdapter(private val context: Context?, private val musicUriList: List<Song>) :
    BaseAdapter() {


    private val inflater: LayoutInflater = LayoutInflater.from(context)

    // Giả sử chỉ muốn hiển thị 3 item giống hệt nhau
    override fun getCount(): Int {
        return musicUriList.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val binding = MusicItemLayoutBinding.inflate(inflater, parent, false)

        val musicUri = musicUriList[position]


        val sArt = Uri.parse("content://media/external/audio/albumart")
        val uri = ContentUris.withAppendedId(sArt, musicUri.albumId).toString()

        if (context != null) {
            Glide.with(context)
                .load(uri)
                .apply(RequestOptions().placeholder(R.drawable.rectangle).centerCrop())
                .into(binding.avatarImageView)
        }
        // Hiển thị thông tin lấy được vào các thành phần UI
        binding.songTitleTextView.text = musicUri.name
        binding.subtitleTextView.text = "subtitleTextView"
        binding.durationTextView.text = formatDuration(musicUri.duration)


        // Thiết lập sự kiện cho nút chỉnh sửa nếu cần
        binding.editButton.setOnClickListener {
            // Xử lý sự kiện khi nhấn nút chỉnh sửa
        }

        return binding.root
    }

    private fun formatDuration(duration: Long): String {
        val minutes = (duration / 1000) / 60
        val seconds = (duration / 1000) % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

}