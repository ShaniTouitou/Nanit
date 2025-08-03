package com.example.nanit.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest

@Composable
fun foxBackground(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val imageLoader = ImageLoader.Builder(context)
        .components {
            add(SvgDecoder.Factory())
        }
        .build()

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .data("file:///android_asset/fox_background.svg")
            .build(),
        imageLoader = imageLoader
    )

    Image(
        painter = painter,
        contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = modifier
    )
}

@Composable
fun elephantBackground(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val imageLoader = ImageLoader.Builder(context)
        .components {
            add(SvgDecoder.Factory())
        }
        .build()

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .data("file:///android_asset/elephant_background.svg")
            .build(),
        imageLoader = imageLoader
    )

    Image(
        painter = painter,
        contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = modifier
    )
}


@Composable
fun pelicanBackground(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val imageLoader = ImageLoader.Builder(context)
        .components {
            add(SvgDecoder.Factory())
        }
        .build()

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .data("file:///android_asset/pelican_background.svg")
            .build(),
        imageLoader = imageLoader
    )

    Image(
        painter = painter,
        contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = modifier
    )
}

