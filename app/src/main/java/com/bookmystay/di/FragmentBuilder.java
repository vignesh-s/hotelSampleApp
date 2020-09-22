package com.bookmystay.di;

import com.bookmystay.ui.hotelDetail.HotelDetailFragment;
import com.bookmystay.ui.postComment.PostCommentFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuilder {

  @ContributesAndroidInjector
  abstract HotelDetailFragment bindHotelDetailFragment();

  @ContributesAndroidInjector
  abstract PostCommentFragment bindPostCommentFragment();

}
