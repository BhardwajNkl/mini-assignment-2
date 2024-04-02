package com.bhardwaj.mini2.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bhardwaj.mini2.DTOs.GenderizeApiResponse;
import com.bhardwaj.mini2.DTOs.NationalizeApiResponse;
import com.bhardwaj.mini2.DTOs.RandomUserApiResponse;
import com.bhardwaj.mini2.entities.UserEntity;
import com.bhardwaj.mini2.exceptions.GenderizeApiException;
import com.bhardwaj.mini2.exceptions.NationalizeApiException;
import com.bhardwaj.mini2.exceptions.RandomUserApiException;
import com.bhardwaj.mini2.exceptions.UserNameException;
import com.bhardwaj.mini2.repositories.UserRepository;
import com.bhardwaj.mini2.sorting.Sorter;
import com.bhardwaj.mini2.sorting.SortingAgeEven;
import com.bhardwaj.mini2.sorting.SortingAgeOdd;
import com.bhardwaj.mini2.sorting.SortingNameEven;
import com.bhardwaj.mini2.sorting.SortingNameOdd;
import com.bhardwaj.mini2.sorting.SortingStrategyFactory;
import com.bhardwaj.mini2.sorting.SortingStrategyInterface;
import com.bhardwaj.mini2.validation.Validator;
import com.bhardwaj.mini2.validation.ValidatorFactory;

@Service
public class UserService {	
	private ExternalApiService externalApiService;
	private UserRepository userRepository;
	private SortingStrategyFactory sortingStrategyFactory;
	private ValidatorFactory validatorFactory;
	
	@Autowired
	public UserService(ExternalApiService externalApiService, UserRepository userRepository,
			SortingStrategyFactory sortingStrategyFactory, ValidatorFactory validatorFactory) {
		this.externalApiService = externalApiService;
		this.userRepository = userRepository;
		this.sortingStrategyFactory = sortingStrategyFactory;
		this.validatorFactory = validatorFactory;
	}
	
	public List<UserEntity> createUsers(int numberOfUsers) {
		// create the users
		List<UserEntity> users = new ArrayList<>();
		try {
			for(int i=0;i<numberOfUsers;i++) {
				UserEntity user = createSingleUser();
				if(user != null) users.add(user);
			}
		}catch (Exception e) {
			throw e;
		}
		// ensure that the required number of users have been fetched and only then save in the database
		if(users.size()==numberOfUsers) {
			users.forEach(user->{userRepository.save(user);});
			return users;
		} else {
			throw new RuntimeException("Error: user creation failed as the requested number of users could not be fetched");
		}
	}
	
	private UserEntity createSingleUser() {
		// get a random user
		RandomUserApiResponse randomUser = null;
		try {
			randomUser = externalApiService.loadRandomUser();
		} catch (Exception e) {
			throw new RandomUserApiException();
		}
		
		// validate the name of the user
		String firstName = randomUser.getResults().get(0).getName().getFirst();
		Validator nameValidator = validatorFactory.createAlphabeticValidator("name");
		nameValidator.validate(firstName);
		
		// get the gender and list of country IDs for this user
		String genderReceivedFromGenderizeApi=null;
		List<String> countryListReceivedFromNationalizeApi=null;
		
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		
		Callable<String> genderizeApiTask = () -> externalApiService.getGenderFromGenderizeApi(firstName);
		Callable<List<String>> nationalizeApiTask = () -> externalApiService.getNationalityFromNationalizeApi(firstName);
		Future<String> genderizeApiResponseFuture = executorService.submit(genderizeApiTask);
		Future<List<String>> nationalizeApiResponseFuture = executorService.submit(nationalizeApiTask);
		
		try {
			try {
				genderReceivedFromGenderizeApi = genderizeApiResponseFuture.get();
			}catch (Exception e) {
				throw new GenderizeApiException();
			}
			try {
				countryListReceivedFromNationalizeApi = nationalizeApiResponseFuture.get();
			} catch (Exception e) {
				throw new NationalizeApiException();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			executorService.shutdown();	
		}
		
		// get verification status based on the above received data
		String verificationStatus = null;
		if(genderReceivedFromGenderizeApi!=null && countryListReceivedFromNationalizeApi!=null) {
			verificationStatus = getVerificationStatus(randomUser, genderReceivedFromGenderizeApi, countryListReceivedFromNationalizeApi);
		} else {
			throw new RuntimeException("Error: gender or nationality data could not be fetched. user creation failed.");
		}
				
		// and, finally build a user entity
		UserEntity userEntity = new UserEntity();
		userEntity.setName(randomUser.getResults().get(0).getName().getFirst()+" "+
		randomUser.getResults().get(0).getName().getLast());
		userEntity.setGender(randomUser.getResults().get(0).getGender());
		userEntity.setAge(randomUser.getResults().get(0).getDob().getAge());
		userEntity.setNationality(randomUser.getResults().get(0).getNat());
		userEntity.setVerificationStatus(verificationStatus);
		
		return userEntity;	
	}
	
	private String getVerificationStatus(RandomUserApiResponse randomUser,
			String genderReceivedFromGenderizeApi, List<String> countryListReceivedFromNationalizeApi) {
		boolean nationalityMatch = countryListReceivedFromNationalizeApi.stream()
				.anyMatch(country->country.equals(randomUser.getResults().get(0).getNat()));
		boolean genderMatch = randomUser.getResults().get(0).getGender().equals(genderReceivedFromGenderizeApi);
		String verificationStatus = "TO_BE_VERIFIED";
		if(nationalityMatch && genderMatch) {
			verificationStatus = "VERIFIED";
		}
		return verificationStatus;
	}
	
	public List<UserEntity> getUsers(int limit, int offset){
		return userRepository.getUsersBasedOnLimitAndOffset(limit, offset);
	}
	
	public List<UserEntity> sortUsers(List<UserEntity> users, String sortType, String sortOrder) {		
		SortingStrategyInterface sortingStrategy = sortingStrategyFactory.createSortingStrategy(sortType, sortOrder);
		Sorter sorter = new Sorter(sortingStrategy);
		return sorter.doSort(users);		
	}
	
	public long getTotalUserCount() {
		return userRepository.count();
	}

}
