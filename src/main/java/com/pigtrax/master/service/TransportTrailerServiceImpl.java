package com.pigtrax.master.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pigtrax.cache.RefDataCache;
import com.pigtrax.master.dao.interfaces.TransportTrailerDao;
import com.pigtrax.master.dto.TransportTrailer;
import com.pigtrax.master.service.interfaces.TransportTrailerService;

@Repository
public class TransportTrailerServiceImpl implements TransportTrailerService{
	
	@Autowired
	private TransportTrailerDao transportTrailerDao;
	
	@Autowired
	RefDataCache refDataCache;

	@Override
	public List<TransportTrailer> getTransportTrailerList(int generatedCompanyId) {
		
		return transportTrailerDao.getTransportTrailerList( generatedCompanyId );
	}
	 
	@Override
	public List<TransportTrailer> getTransportTrailerList(int generatedCompanyId, String language) {
		List<TransportTrailer> trailerList = transportTrailerDao.getTransportTrailerList( generatedCompanyId );
		if(trailerList != null && 0 <trailerList.size())
		{
			for(TransportTrailer trailer : trailerList)
			{
				trailer.setTrailerFunction(refDataCache.getTrailerFunctionMap(language).get(trailer.getTrailerFunctionId()));
			}
		}
		return trailerList;
	}

	@Override
	public int insertTransportTrailer(TransportTrailer transportTrailer)
			throws SQLException {
		return transportTrailerDao.insertTransportTrailer(transportTrailer);
	}

	@Override
	public int updateTransportTrailer(TransportTrailer transportTrailer)
			throws SQLException {
		return transportTrailerDao.updateTransportTrailer(transportTrailer);
	}

	@Override
	public TransportTrailer findByTransportTrailerByAutoGeneratedId(
			int generatedTransportTrailerId) throws SQLException {
		return transportTrailerDao.findByTransportTrailerByAutoGeneratedId(generatedTransportTrailerId);
	}
	
	@Override
	public int deleteTransportTrailer(TransportTrailer transportTrailer)
			throws SQLException {
		return transportTrailerDao.deleteTransportTrailer(transportTrailer);
	}

}
