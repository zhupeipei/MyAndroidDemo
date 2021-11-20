package com.aire.android.image;

import android.content.Context;

import androidx.annotation.NonNull;

import com.aire.android.test.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

/**
 * @author ZhuPeipei
 * @date 2021/11/20 14:24
 */
//@GlideModule
public class MyGlideModule extends AppGlideModule {
    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        super.applyOptions(context, builder);
        builder.setDiskCache(new DiskLruCacheFactory(
                context.getExternalCacheDir().getAbsolutePath(),
                "imageCache",
                1024 * 1024 * 50));
        builder.setDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.ic_launcher_background));
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);
    }
}
