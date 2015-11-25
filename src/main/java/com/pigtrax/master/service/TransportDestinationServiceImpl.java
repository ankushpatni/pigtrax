package com.pigtrax.master.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pigtrax.cache.RefDataCache;
import com.pigtrax.master.dao.interfaces.TransportDestinationDao;
import com.pigtrax.master.dto.TransportDestination;
import com.pigtrax.master.service.interfaces.TransportDestinationService;

@Repository
public class TransportDestinationServiceImpl implements TransportDestinationService {

	@Autowired
	private TransportDestinationDao transportDestinationDao;
	
	@Autowired
	RefDataCache refDataCache;
	
	@Override
	public List<TransportDestination> getTransportDestinationList(
			int generatedCompanyId) {
		List<TransportDestination> destinationList = transportDestinationDao.getTransportDestinationList(generatedCompanyId);
		if(destinationList != null && 0< destinationList.size())
		{
			for(TransportDestination destination : destinationList)
			{
				destination.setMarketType(refDataCache.getMarketTypeMap("en").get(destination.getMarketTypeId()));
			}
		}
		return destinationList;
	}
	
	@Override
	public List<TransportDestination> getTransportDestinationList(
			int generatedCompanyId, String language) {
		List<TransportDestination> destinationList = transportDestinationDao.getTransportDestinationList(generatedCompanyId);
		if(destinationList != null && 0< destinationList.size())
		{
			for(TransportDestination destination : destinationList)
			{
				destination.setMarketType(refDataCache.getMarketTypeMap(language).get(destination.getMarketTypeId()));
			}
		}
		return destinationList;
	}

	@Override
	public int insertTransportDestinationRecord(
			TransportDestination transportDestination) throws SQLException {
		return transportDestinationDao.insertTransportDestinationRecord(transportDestination);
	}

	@Override
	public int updateTransportDestinationRecord(
			TransportDestination transportDestination) throws SQLException {
		return transportDestinationDao.updateTransportDestinationRecord(transportDestination);
	}

	@Override
	public TransportDestination findByTransportDestinationByAutoGeneratedId(
			int generatedTransportDestinationId) throws SQLException {
		return transportDestinationDao.findByTransportDestinationByAutoGeneratedId(generatedTransportDestinationId);
	}

}
