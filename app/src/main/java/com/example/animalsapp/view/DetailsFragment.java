package com.example.animalsapp.view;


import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.palette.graphics.Palette;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.animalsapp.R;
import com.example.animalsapp.databinding.FragmentDetailsBinding;
import com.example.animalsapp.model.AnimalModel;
import com.example.animalsapp.model.AnimalPalette;

public class DetailsFragment extends Fragment {

    private AnimalModel animal;
    FragmentDetailsBinding animalDetailBinding;

    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Data binding view
        animalDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false);

        return animalDetailBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            //Getting Arguments from Navigation
            animal = DetailsFragmentArgs.fromBundle(getArguments()).getAnimalModel();
        }

        if (animal != null) {
            animalDetailBinding.setAnimal(animal);
            setupBackgroundColor(animal.imageUrl);
        }
    }

    private void setupBackgroundColor(String imageUrl) {
        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Palette.from(resource).generate(palette -> {
                            Palette.Swatch color = palette.getLightMutedSwatch();

                            if (color != null) {
                                int intColor = palette.getLightMutedSwatch().getRgb();
                                AnimalPalette animalPalette = new AnimalPalette(intColor);
                                animalDetailBinding.setPalette(animalPalette);
                            }
                        });
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        //No need to put anything here
                    }
                });
    }
}
