package com.example.animalsapp.view;


import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.palette.graphics.Palette;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.animalsapp.R;
import com.example.animalsapp.model.AnimalModel;
import com.example.animalsapp.util.Util;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsFragment extends Fragment {

    private AnimalModel animal;

    @BindView(R.id.animalImage)
    ImageView animalImage;

    @BindView(R.id.animalName)
    TextView animalName;

    @BindView(R.id.animalLocation)
    TextView animalLocation;

    @BindView(R.id.animalLifespan)
    TextView animalLifespan;

    @BindView(R.id.animalDiet)
    TextView animalDiet;

    @BindView(R.id.animalLinearLayout)
    LinearLayout animalLinearLayout;


    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            //Getting Arguments from Navigation
            animal = DetailsFragmentArgs.fromBundle(getArguments()).getAnimalModel();
        }

        if (animal != null) {
            animalName.setText(animal.name);
            animalLocation.setText(animal.location);
            animalLifespan.setText(animal.lifeSpan);
            animalDiet.setText(animal.diet);

            Util.loadImage(animalImage, animal.imageUrl, Util.getProgressDrawable(animalImage.getContext()));

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
                                animalLinearLayout.setBackgroundColor(intColor);
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
