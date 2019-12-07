package com.bookmystay.repository;

import com.bookmystay.data.model.Comment;
import com.bookmystay.data.model.Hotel;
import com.bookmystay.repository.api.HotelApiService;
import com.bookmystay.repository.local.PreferencesHelper;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static junit.framework.Assert.fail;
import static org.mockito.Mockito.verify;

public class HotelRepositoryImplTest {

  @Mock
  private List<Comment> mockedCommentsList;

  private HotelApiService mHotelApiService;
  private PreferencesHelper mPreferencesHelper;
  private NetworkChecker mNetworkChecker;
  private HotelRepositoryImpl mHotelRepositoryImpl;

  @Before
  public void setUp() {
    mHotelApiService = Mockito.mock(HotelApiService.class);
    mPreferencesHelper = Mockito.mock(PreferencesHelper.class);
    mNetworkChecker = Mockito.mock(NetworkChecker.class);
    mHotelRepositoryImpl = Mockito.spy(
            new HotelRepositoryImpl(mHotelApiService, mPreferencesHelper, mNetworkChecker)
    );
  }

  @Test
  public void testGetHotelDetails_WhenNetworkAvailable_GetsDataFromRemote() {

    Mockito.when(mNetworkChecker.isConnectedToInternet()).thenReturn(true);

    try {
      mHotelRepositoryImpl.getHotelDetails();
    } catch (Exception e) {
      e.printStackTrace();
      fail(" Exception: " + e.getMessage());
    }

    verify(mHotelApiService).doGetHotelDetailsApiCall();
  }

  @Test
  public void testGetHotelDetails_WhenNetworkUnavailable_GetsDataFromLocal() {

    Mockito.when(mNetworkChecker.isConnectedToInternet()).thenReturn(false);

    try {
      mHotelRepositoryImpl.getHotelDetails();
    } catch (Exception e) {
      e.printStackTrace();
      fail(" Exception: " + e.getMessage());
    }

    verify(mHotelRepositoryImpl).getHotelDetailsFromLocal();
  }

  @Test
  public void testSaveHotelDetails_SetHotelDetailInPreferencesHelper() {

    Hotel hotel = Mockito.mock(Hotel.class);

    try {
      mHotelRepositoryImpl.saveHotelDetails(hotel);
    } catch (Exception e) {
      e.printStackTrace();
      fail(" Exception: " + e.getMessage());
    }

    verify(mPreferencesHelper).setHotelDetail(hotel);
  }

  @Test
  public void testGetComments_WhenNetworkAvailable_GetsDataFromRemote() {

    Mockito.when(mNetworkChecker.isConnectedToInternet()).thenReturn(true);

    try {
      mHotelRepositoryImpl.getHotelComments();
    } catch (Exception e) {
      e.printStackTrace();
      fail(" Exception: " + e.getMessage());
    }

    verify(mHotelApiService).doGetHotelCommentsApiCall();
  }

  @Test
  public void testGetComments_WhenNetworkUnavailable_GetsDataFromLocal() {

    Mockito.when(mNetworkChecker.isConnectedToInternet()).thenReturn(false);

    try {
      mHotelRepositoryImpl.getHotelComments();
    } catch (Exception e) {
      e.printStackTrace();
      fail(" Exception: " + e.getMessage());
    }

    verify(mHotelRepositoryImpl).getCommentsFromLocal();
  }

  @Test
  public void testSaveComments_SetCommentsInPreferencesHelper() {

    try {
      mHotelRepositoryImpl.saveComments(mockedCommentsList);
    } catch (Exception e) {
      e.printStackTrace();
      fail(" Exception: " + e.getMessage());
    }

    verify(mPreferencesHelper).setComments(mockedCommentsList);
  }
}
