package br.edu.ifpb.dac.ssp.business.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.dac.ssp.model.entity.Place;
import br.edu.ifpb.dac.ssp.model.repository.PlaceRepository;
import br.edu.ifpb.dac.ssp.presentation.exception.MissingFieldException;
import br.edu.ifpb.dac.ssp.presentation.exception.ObjectAlreadyExistsException;
import br.edu.ifpb.dac.ssp.presentation.exception.ObjectNotFoundException;

@Service
public class PlaceService {
	
	@Autowired
	private PlaceRepository placeRepository;
	
	public List<Place> findAll() {
		return placeRepository.findAll();
	}
	
	public boolean existsById(Integer id) {
		return placeRepository.existsById(id);
	}
	
	public boolean existsByName(String name) {
		return placeRepository.existsByName(name);
	}
	
	public Place findById(Integer id) throws Exception {
		if (id == null) {
			throw new MissingFieldException("id");
		}
		
		if (!existsById(id)) {
			throw new ObjectNotFoundException("local", "id", id);
		}
		return placeRepository.getById(id);
	}
	
	public Optional<Place> findByName(String name) throws Exception {
		if (name == null || name.isBlank()) {
			throw new MissingFieldException("nome");
		}
		
		if (!existsByName(name)) {
			throw new ObjectNotFoundException("local", "nome", name);
		}
		return placeRepository.findByName(name);
	}
	
	public Place save(Place place) throws Exception {
		if (place.getName() == null || place.getName().isBlank()) {
			throw new MissingFieldException("nome", "save");
		}
		
		if (existsByName(place.getName())) {
			throw new ObjectAlreadyExistsException("Já existe um local com nome " + place.getName());
		}
		
		return placeRepository.save(place);
	}
	
	public Place update(Place place) throws Exception {
		if (place.getName() == null || place.getName().isBlank()) {
			throw new MissingFieldException("nome", "update");
		}
		
		if (place.getId() == null) {
			throw new MissingFieldException("id", "update");
		} else if (!existsById(place.getId())) {
			throw new ObjectNotFoundException("local", "id", place.getId());
		} 
		
		if (existsByName(place.getName())) {
			Place placeSaved = findByName(place.getName()).get();
			if (placeSaved.getId() != place.getId()) {
				throw new ObjectAlreadyExistsException("Já existe um local com nome " + place.getName());
			}
		}

		return placeRepository.save(place);
	}
	
	public void delete(Place place) throws Exception {
		if (place.getId() == null) {
			throw new MissingFieldException("id", "delete");
		} else if (!existsById(place.getId())) {
			throw new ObjectNotFoundException("local", "id", place.getId());
		}
		
		placeRepository.delete(place);
	}
	
	public void deleteById(Integer id) throws Exception {
		if (id == null) {
			throw new MissingFieldException("id", "delete");
		} else if (!existsById(id)) {
			throw new ObjectNotFoundException("local", "id", id);
		}
		
		placeRepository.deleteById(id);
	}
}

