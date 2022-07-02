package com.kodlamaio.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.CarService;
import com.kodlamaio.rentACar.business.request.cars.CreateCarRequest;
import com.kodlamaio.rentACar.business.request.cars.DeleteCarRequest;
import com.kodlamaio.rentACar.business.request.cars.UpdateCarRequest;
import com.kodlamaio.rentACar.business.response.cars.GetAllCarsResponse;
import com.kodlamaio.rentACar.business.response.cars.ReadCarResponse;
import com.kodlamaio.rentACar.core.utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.CarRepository;
import com.kodlamaio.rentACar.entities.concretes.Car;

@Service
public class CarManager implements CarService {

	 CarRepository carRepository;
	 ModelMapperService modelMapperService;

	@Autowired
	public CarManager(CarRepository carRepository, ModelMapperService modelMapperService) {
		super();
		this.carRepository = carRepository;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateCarRequest createCarRequest) {
		Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class); //araba eklendiğinde durum müsait
		car.setState(1);
		checkMaxBrand(createCarRequest.getBrandId());
		this.carRepository.save(car);
		return new SuccessResult("CAR.ADDED");
		

	}

	@Override
	public Result update(UpdateCarRequest updateCarRequest) {
		checkIfCarExistById(updateCarRequest.getId());
		Car car = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);
		checkMaxBrand(updateCarRequest.getBrandId(), car, updateCarRequest);
		checkMaxBrand(updateCarRequest.getBrandId());
		this.carRepository.save(car);
		return new SuccessResult("UPDATED.CAR");

	}

	@Override
	public Result delete(DeleteCarRequest deleteCarRequest) {
		checkIfCarExistById(deleteCarRequest.getId());
		Car car = this.modelMapperService.forRequest().map(deleteCarRequest, Car.class);
		this.carRepository.delete(car);
		return new SuccessResult("DELETED.CAR");

	}

	//caching yaptık bu sayede ilk işlemden sonra her seferinde ön bellekten çekicek ve daha hızlı çalışacak.(4 sn)
	@Cacheable("cars")
	@Override
	public DataResult<List<GetAllCarsResponse>> getAll() {
		List<Car> cars = this.carRepository.findAll();
		List<GetAllCarsResponse> response = cars.stream()
				.map(car -> this.modelMapperService.forResponse().map(car, GetAllCarsResponse.class))
				.collect(Collectors.toList());
		
		try {
			Thread.sleep(1000*4);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return new SuccessDataResult<List<GetAllCarsResponse>>(response);
	}

	@Override
	public DataResult<ReadCarResponse> getById(int id) {
		Car car=checkIfCarExistById(id);
		ReadCarResponse readCarResponse=this.modelMapperService.forResponse().map(car, ReadCarResponse.class);
		return new SuccessDataResult<ReadCarResponse>(readCarResponse);
	}

	//aynı markadan 5den fazla olamaz
	private boolean maxBrand(int brandId) {
		boolean exits = false;
		if (carRepository.getByBrandId(brandId).size() < 5) {
			exits = true;
		} 
		return exits;
	}
	//5den fazlaysa hata ver
	private void checkMaxBrand(int id) {
		if(!maxBrand(id)) {
			throw new BusinessException("CAR.NOT.ADDED");
		}
	}
	//
	private void checkMaxBrand(int id, Car car, UpdateCarRequest updateCarRequest) {     //**
		if (carRepository.getByBrandId(updateCarRequest.getBrandId()).size() == 5) {
			car = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);
			this.carRepository.save(car);
		}

	}
	
	private Car checkIfCarExistById(int id) {  //iddeki elemanlara ulşmak için
		Car currentCar;
		try {
			currentCar = this.carRepository.findById(id).get();

		} catch (Exception e) {
			throw new BusinessException("RENTAL.NOT.EXITS");
		}
		return currentCar;
	}

	@Override
	public Car getCarById(int id) {   //carServise ulaşmak için
		
		return  checkIfCarExistById(id);
	}

	
	

}
