package com.match.matrimony.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.match.matrimony.constants.ApplicationConstants;
import com.match.matrimony.dto.DashboardResponse;
import com.match.matrimony.dto.DashboardResponseDto;
import com.match.matrimony.dto.FavouriteProfileRequestDto;
import com.match.matrimony.dto.FavouriteProfileResponsedto;
import com.match.matrimony.dto.Favourites;
import com.match.matrimony.dto.LoginRequestDto;
import com.match.matrimony.dto.LoginResponseDto;
import com.match.matrimony.dto.UserProfileResponsedto;
import com.match.matrimony.dto.UserRegistrationRequestDto;
import com.match.matrimony.dto.UserRegistrationResponseDto;
import com.match.matrimony.entity.UserFavourite;
import com.match.matrimony.entity.UserProfile;
import com.match.matrimony.exception.GeneralException;
import com.match.matrimony.exception.ProfileNotFoundException;
import com.match.matrimony.exception.UserProfileException;
import com.match.matrimony.service.UserProfileService;
import com.match.matrimony.service.UserRegistrationService;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserProfileControllerTest {

	@InjectMocks
	UserProfileController userProfileController;
	@Mock
	UserProfileService userProfileService;
	@Mock
	UserRegistrationService userRegistrationService;

	UserRegistrationRequestDto userRegistrationRequestDto = new UserRegistrationRequestDto();
	UserRegistrationResponseDto userRegistrationResponseDto = new UserRegistrationResponseDto();

	List<DashboardResponse> dashboardResponses = null;
	DashboardResponse dashboardResponse = null;
	DashboardResponseDto dashboardResponseDto = null;

	UserProfile userProfile = null;
	LoginRequestDto loginRequestDto = null;
	LoginRequestDto loginRequestDto1 = null;
	LoginResponseDto loginResponseDto = null;

	UserProfile userProfile2 = new UserProfile();
	List<Favourites> favouritesList = new ArrayList<>();
	UserFavourite userFavourite = new UserFavourite();
	Favourites favourites = new Favourites();

	UserProfileResponsedto userProfileResponsedto = new UserProfileResponsedto();
	FavouriteProfileRequestDto favouriteProfileRequestDto = new FavouriteProfileRequestDto();
	FavouriteProfileResponsedto favouriteProfileResponsedto = new FavouriteProfileResponsedto();
	

	@Before
	public void setUp() {
		userRegistrationRequestDto.setEmail("ans@gmail.com");
		userRegistrationRequestDto.setExpectation("looking for job holder");
		userRegistrationRequestDto.setGender("male");
		userRegistrationRequestDto.setMobile(9403347L);
		userRegistrationRequestDto.setMotherTongue("english");
		userRegistrationRequestDto.setPlace("karnataka");
		userRegistrationRequestDto.setProfession("doctor");
		userRegistrationRequestDto.setReligion("hindu");
		userRegistrationRequestDto.setSalary(35644.50);
		userRegistrationRequestDto.setDateOfBirth(LocalDate.now());
		userRegistrationResponseDto.setStatusCode(200);
		userRegistrationResponseDto.setMessage("success");
		userRegistrationResponseDto.setUserProfileId(1004L);
	}

	@Test
	public void matrimonyRegistration() throws GeneralException {
		Mockito.when(userRegistrationService.matrimonyRegistration(userRegistrationRequestDto))
				.thenReturn(Optional.of(userRegistrationResponseDto));
		ResponseEntity<Optional<UserRegistrationResponseDto>> response = userProfileController
				.matrimonyRegistration(userRegistrationRequestDto);
		Assert.assertNotNull(response);

	}

	@Test
	public void matrimonyRegistrationNegative() throws GeneralException {
		Mockito.when(userRegistrationService.matrimonyRegistration(userRegistrationRequestDto))
				.thenReturn(Optional.ofNullable(null));
		ResponseEntity<Optional<UserRegistrationResponseDto>> response = userProfileController
				.matrimonyRegistration(userRegistrationRequestDto);

		Assert.assertNotNull(response);
	}

	@Before
	public void before() {
		dashboardResponses = new ArrayList<>();
		dashboardResponse = new DashboardResponse();
		dashboardResponse.setProfession("Engineer");
		dashboardResponses.add(dashboardResponse);

		dashboardResponseDto = new DashboardResponseDto();
		dashboardResponseDto.setDashboardResponses(dashboardResponses);

		userProfile = new UserProfile();
		loginRequestDto = new LoginRequestDto();
		loginResponseDto = new LoginResponseDto();

		userProfile.setUserProfileId(1L);
		userProfile.setUserProfilePassword("muthu123");

		loginRequestDto.setUserProfileId(1L);
		loginRequestDto.setUserProfilePassword("muthu123");
		loginResponseDto.setStatusCode(ApplicationConstants.USERPROFILE_SUCCESS_CODE);
		loginResponseDto.setMessage(ApplicationConstants.USERPROFILE_SUCCESS_MESSAGE);

		loginRequestDto1 = new LoginRequestDto();
		loginResponseDto.setStatusCode(ApplicationConstants.USERPROFILE_FAILURE_CODE);
		loginResponseDto.setMessage(ApplicationConstants.USERPROFILE_FAILURE_MESSAGE);

		userProfile2.setUserProfileId(1L);
		userFavourite.setUserFavouriteId(1L);
		userFavourite.setUserMatchId(userProfile2);
		favourites.setUserProfileId(2L);
		favouritesList.add(favourites);

		userProfileResponsedto.setUserProfileId(1L);

		favouriteProfileRequestDto.setUserMatchId(2L);
		favouriteProfileRequestDto.setUserProfileId(1L);

		favouriteProfileRequestDto.setUserMatchId(2L);
		favouriteProfileRequestDto.setUserProfileId(1L);

		favouriteProfileResponsedto.setMessage("Sucess");
	}

	@Test
	public void matchListForPositive() {
		Mockito.when(userProfileService.matchList(1L)).thenReturn(Optional.of(dashboardResponses));
		Integer status = userProfileController.matchList(1L).getStatusCodeValue();
		assertEquals(200, status);
	}

	@Test
	public void matchListForNegative() {
		Optional<List<DashboardResponse>> dashboardResponses1 = Optional.ofNullable(null);
		Mockito.when(userProfileService.matchList(2L)).thenReturn(dashboardResponses1);
		Integer status = userProfileController.matchList(2L).getStatusCodeValue();
		assertEquals(404, status);
	}

	@Test
	public void testUserLoginPositive() {
		Mockito.when(userProfileService.userLogin(loginRequestDto.getUserProfileId(),
				loginRequestDto.getUserProfilePassword())).thenReturn(Optional.of(new UserProfile()));
		Integer expected = userProfileController.userLogin(loginRequestDto).getStatusCodeValue();
		assertEquals(ApplicationConstants.USERPROFILE_SUCCESS_CODE, expected);
	}

	@Test
	public void testUserLoginNegative() {
		Mockito.when(userProfileService.userLogin(loginRequestDto1.getUserProfileId(),
				loginRequestDto1.getUserProfilePassword())).thenReturn(Optional.of(new UserProfile()));
		Integer expected = userProfileController.userLogin(loginRequestDto).getStatusCodeValue();
		assertEquals(ApplicationConstants.USERPROFILE_FAILURE_CODE, expected);
	}

	@Test
	public void testViewFavouritesSuccess() throws ProfileNotFoundException {
		Mockito.when(userProfileService.viewFavourites(1L)).thenReturn(favouritesList);
		Integer expected = userProfileController.viewFavourites(1L).getStatusCodeValue();
		assertEquals(ApplicationConstants.USERPROFILE_SUCCESS_CODE, expected);
	}

	@Test
	public void testViewFavouritesNegative() throws ProfileNotFoundException {
		Mockito.when(userProfileService.viewFavourites(2L)).thenReturn(favouritesList);
		Integer expected = userProfileController.viewFavourites(1L).getStatusCodeValue();
		assertEquals(ApplicationConstants.USERPROFILE_FAILURE_CODE, expected);
	}

	@Test
	public void testViewProfile() throws UserProfileException {
		Mockito.when(userProfileService.viewProfile(1L)).thenReturn(Optional.of(userProfileResponsedto));
		ResponseEntity<Optional<UserProfileResponsedto>> userProfileResponsedto = userProfileController.viewProfile(1L);
		Assert.assertNotNull(userProfileResponsedto);
	}

	@Test
	public void testViewProfileNegative() throws UserProfileException {
		Mockito.when(userProfileService.viewProfile(2L)).thenReturn(Optional.ofNullable(null));
		ResponseEntity<Optional<UserProfileResponsedto>> userProfileResponsedto = userProfileController.viewProfile(1L);
		Assert.assertNotNull(userProfileResponsedto);
	}

	@Test
	public void testAddFavourite() throws UserProfileException {
		Mockito.when(userProfileService.addFavourite(favouriteProfileRequestDto))
				.thenReturn(Optional.of(favouriteProfileResponsedto));
		ResponseEntity<Optional<FavouriteProfileResponsedto>> favouriteProfileResponse = userProfileController
				.addFavourite(favouriteProfileRequestDto);
		Assert.assertNotNull(favouriteProfileResponse);
	}

	@Test
	public void testAddFavouriteNegative() throws UserProfileException {
		Mockito.when(userProfileService.addFavourite(favouriteProfileRequestDto)).thenReturn(Optional.ofNullable(null));
		ResponseEntity<Optional<FavouriteProfileResponsedto>> favouriteProfileResponse = userProfileController
				.addFavourite(favouriteProfileRequestDto);

		Assert.assertNotNull(favouriteProfileResponse);
	}
}
