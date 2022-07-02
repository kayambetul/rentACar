package com.kodlamaio.rentACar.business.concretes;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.CarService;
import com.kodlamaio.rentACar.business.abstracts.CorporateCustomerService;
import com.kodlamaio.rentACar.business.abstracts.FindexService;
import com.kodlamaio.rentACar.business.abstracts.IndividualCustomerService;
import com.kodlamaio.rentACar.business.abstracts.RentalService;
import com.kodlamaio.rentACar.business.request.rental.CreateCorporateCustomerRentalRequest;
import com.kodlamaio.rentACar.business.request.rental.CreateIndividualCustomerRentalRequest;
import com.kodlamaio.rentACar.business.request.rental.DeleteIndividualCustomerRentalRequest;
import com.kodlamaio.rentACar.business.request.rental.UpdateCorporateCustomerRentalRequest;
import com.kodlamaio.rentACar.business.request.rental.UpdateIndividualCustomerRentalRequest;
import com.kodlamaio.rentACar.business.response.rentals.GetAllRentalResponse;
import com.kodlamaio.rentACar.business.response.rentals.ReadRentalResponse;
import com.kodlamaio.rentACar.core.utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.RentalRepository;
import com.kodlamaio.rentACar.entities.concretes.Car;
import com.kodlamaio.rentACar.entities.concretes.CorporateCustomer;
import com.kodlamaio.rentACar.entities.concretes.IndividualCustomer;
import com.kodlamaio.rentACar.entities.concretes.Rental;

@Service
public class RentalManager implements RentalService {

	RentalRepository rentalRepository;
	CarService carService;
	ModelMapperService modelMapperService;
	FindexService findexService;
	IndividualCustomerService individualCustomerService;
	CorporateCustomerService corporateCustomerService;

	@Autowired
	public RentalManager(RentalRepository rentalRepository, CarService carService, 
			ModelMapperService modelMapperService, FindexService findexService,
			IndividualCustomerService individualCustomerService,CorporateCustomerService corporateCustomerService) {
		this.rentalRepository = rentalRepository;
		this.carService = carService;
		this.modelMapperService = modelMapperService;
		this.findexService = findexService;
		this.individualCustomerService = individualCustomerService;
		this.corporateCustomerService=corporateCustomerService;
	}

	@Override
	public Result addIndividual(CreateIndividualCustomerRentalRequest createIndividualCustomerRentalRequest) {
		Car car = this.carService.getCarById(createIndividualCustomerRentalRequest.getCarId());
		IndividualCustomer individualCustomer = this.individualCustomerService.getIndividualCustomerById(createIndividualCustomerRentalRequest.getIndividualCustomerId());		

		checkCarAvailable(car.getId());

		Rental rental = this.modelMapperService.forRequest().map(createIndividualCustomerRentalRequest, Rental.class);
		rental.setReturnDate(calculateReturnDate(createIndividualCustomerRentalRequest.getPickupDate(),
				createIndividualCustomerRentalRequest.getTotalDays()));
		rental.setTotalPrice(calculateTotalPrice(createIndividualCustomerRentalRequest.getTotalDays(), car, rental));

		checkFindexValue(car.getMinFindex(), individualCustomer.getNationality());

		this.rentalRepository.save(rental);
		return new SuccessResult("ADDED.RENTAL");

	}

	@Override
	public Result addCorporete(CreateCorporateCustomerRentalRequest createCorporateCustomerRentalRequest) {
		Car car = this.carService.getCarById(createCorporateCustomerRentalRequest.getCarId());
		CorporateCustomer corporateCustomer=this.corporateCustomerService.getCorporateCustomerById(createCorporateCustomerRentalRequest.getCorporateCustomerId());
		checkCarAvailable(car.getId());

		Rental rental = this.modelMapperService.forRequest().map(createCorporateCustomerRentalRequest, Rental.class);
		rental.setReturnDate(calculateReturnDate(createCorporateCustomerRentalRequest.getPickupDate(),
				createCorporateCustomerRentalRequest.getTotalDays()));
		rental.setTotalPrice(calculateTotalPrice(createCorporateCustomerRentalRequest.getTotalDays(), car, rental));
		this.rentalRepository.save(rental);
		return new SuccessResult("ADDED.RENTAL");
	}

	@Override
	public Result updateIndividual(UpdateIndividualCustomerRentalRequest updateIndividualCustomerRentalRequest) {
		checkIfRentalExistsById(updateIndividualCustomerRentalRequest.getId());
		Car car = this.carService.getCarById(updateIndividualCustomerRentalRequest.getCarId());
		IndividualCustomer individualCustomer = this.individualCustomerService.getIndividualCustomerById(updateIndividualCustomerRentalRequest.getIndividualCustomerId());

		Rental rental = this.modelMapperService.forRequest().map(updateIndividualCustomerRentalRequest, Rental.class);
		rental.setReturnDate(calculateReturnDate(updateIndividualCustomerRentalRequest.getPickupDate(),
				updateIndividualCustomerRentalRequest.getTotalDays()));
		rental.setTotalPrice(calculateTotalPrice(updateIndividualCustomerRentalRequest.getTotalDays(), car, rental));

		checkCarChangeId(updateIndividualCustomerRentalRequest.getCarId(), car);

		checkFindexValue(car.getMinFindex(), individualCustomer.getNationality());

		this.rentalRepository.save(rental);
		return new SuccessResult("UPDATED.RENTAL");
	}

	@Override
	public Result updateCorporate(UpdateCorporateCustomerRentalRequest updateCorporateCustomerRentalRequest) {
		checkIfRentalExistsById(updateCorporateCustomerRentalRequest.getCorporateCustomerId());
		Car car = this.carService.getCarById(updateCorporateCustomerRentalRequest.getCarId());
		CorporateCustomer corporateCustomer=this.corporateCustomerService.getCorporateCustomerById(updateCorporateCustomerRentalRequest.getCorporateCustomerId());
		Rental rental = this.modelMapperService.forRequest().map(updateCorporateCustomerRentalRequest, Rental.class);
		rental.setReturnDate(calculateReturnDate(updateCorporateCustomerRentalRequest.getPickupDate(),
				updateCorporateCustomerRentalRequest.getTotalDays()));
		rental.setTotalPrice(calculateTotalPrice(updateCorporateCustomerRentalRequest.getTotalDays(), car, rental));

		checkCarChangeId(updateCorporateCustomerRentalRequest.getCorporateCustomerId(), car);

		this.rentalRepository.save(rental);
		return new SuccessResult("UPDATED.RENTAL");
	}

	@Override
	public Result deleteIndividual(DeleteIndividualCustomerRentalRequest deleteRentalRequest) {
		checkIfRentalExistsById(deleteRentalRequest.getId());
		Rental rental = this.modelMapperService.forRequest().map(deleteRentalRequest, Rental.class);
		this.rentalRepository.delete(rental);
		return new SuccessResult("DELETED.RENTAL");
	}

	@Override
	public DataResult<ReadRentalResponse> getById(int id) {
		Rental rental = checkIfRentalExistsById(id);
		ReadRentalResponse response = this.modelMapperService.forResponse().map(rental, ReadRentalResponse.class);
		return new SuccessDataResult<ReadRentalResponse>(response);
	}

	@Override
	public DataResult<List<GetAllRentalResponse>> getAll() {
		List<Rental> rentals = this.rentalRepository.findAll();
		List<GetAllRentalResponse> responses = rentals.stream()
				.map(rental -> this.modelMapperService.forResponse().map(rental, GetAllRentalResponse.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllRentalResponse>>(responses);
	}

	private void checkFindexValue(int findexScore, String nationality) {   //findex score değeri

		if (findexService.findexScore(nationality) < findexScore) {
			throw new BusinessException("NOT.ENOUGH.FİNDEX.SCORE");
		}

	}


	private void checkCarAvailable(int carId) {        //Car müsaitlikten rental durumuna geçmesi

		Car car = this.carService.getCarById(carId);
		if (car.getState() == 1) {
			car.setState(3);
		} else {
			throw new BusinessException("NOT.CAR.AVAILABLE");
		}

	}

	private LocalDate calculateReturnDate(LocalDate pickUpDate, int totalDays) {      //rental bitme tarihi hesaplama
		LocalDate returnvalue = pickUpDate.plusDays(totalDays);
		return returnvalue;

	}

	private double calculateTotalPrice(int totalDays, Car car, Rental rental) {         //totalPrice hesaplama 
		double totalPrice = totalDays * car.getDailyPrice();

		if (!rental.getPickUpCity().equals(rental.getReturnCity())) {       //farklı şehirlere gitme durumunda totalPrice hesaplama

			totalPrice += 750;
		}

		return totalPrice;
	}

	private Rental checkIfRentalExistsById(int id) {          //rental için id
		Rental currentRental;
		try {
			currentRental = this.rentalRepository.findById(id).get();
		} catch (Exception e) {
			throw new BusinessException("RENTAL.NOT.EXISTS");
		}
		return currentRental;
	}

	private void checkCarChangeId(int id, Car car) {        //car değişikliği durumunda geri verilen car müsaitlik durumuna çekme
		Rental rental = this.rentalRepository.findById(id).get();
		Car tempCar = rental.getCar();

		if (car.getId() != tempCar.getId()) {
			tempCar.setState(1);
			checkCarAvailable(car.getId());
		}

	}

	

	private void checkIfTaxNumberExist(int corporateCustomerId) {  
		CorporateCustomer corporateCustomer;
		try {
			corporateCustomer = this.corporateCustomerService.getCorporateCustomerById(corporateCustomerId);
		} catch (Exception e) {
			throw new BusinessException("CORPORATE.NOT.EXISTS");
		}
	}

	

	@Override
	public Rental getRentalById(int id) {
		
		return checkIfRentalExistsById(id);
	}

}
