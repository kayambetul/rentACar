package com.kodlamaio.rentACar.core.adapters;

import java.rmi.RemoteException;

import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.UserCheckService;
import com.kodlamaio.rentACar.entities.concretes.IndividualCustomer;

import tr.gov.nvi.tckimlik.WS.KPSPublicSoapProxy;

@Service
public class MernisKpsService implements UserCheckService {

	@Override
	public boolean checkIfRealPerson(IndividualCustomer individualCustomer) {
		boolean state = true;
//		try {
//			KPSPublicSoapProxy kpsPublicSoapProxy = new KPSPublicSoapProxy();
//			boolean isValidPerson = kpsPublicSoapProxy.TCKimlikNoDogrula(
//					Long.parseLong(individualCustomer.getNationality()),
//					individualCustomer.getFirstName().toUpperCase(), individualCustomer.getLastName().toUpperCase(),
//					individualCustomer.getBirthDate());
//			System.out.println(isValidPerson);
//					
//			return isValidPerson;
//		} catch (Exception e) {
//			System.out.println("Kişi bulunamadı");
//		}
//		return false;
		
		boolean isValidPerson;
		if (state) {
			KPSPublicSoapProxy kpsPublicSoapProxy = new KPSPublicSoapProxy();

			try {
				isValidPerson = kpsPublicSoapProxy.TCKimlikNoDogrula(
						Long.parseLong(individualCustomer.getNationality()),
						individualCustomer.getFirstName().toUpperCase(), individualCustomer.getLastName().toUpperCase(),
						individualCustomer.getBirthDate());
				return isValidPerson;
			} catch (NumberFormatException | RemoteException e) {
				
				e.printStackTrace();
			}

		}
		return true;

	}

}
