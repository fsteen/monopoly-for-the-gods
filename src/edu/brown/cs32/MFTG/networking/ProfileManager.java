package edu.brown.cs32.MFTG.networking;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.brown.cs32.MFTG.tournament.Profile;

public class ProfileManager {
	private final String DEFAULT_PATH = ".profiles.mf";
	
	Map<String, Profile> _profiles;
	
	public ProfileManager(){
		buildProfileMap(null);
	}
	
	public ProfileManager(String filePath){
		buildProfileMap(filePath);
	}
	
	/**
	 * Builds the profile map from the given input file
	 * @param filePath
	 */
	private void buildProfileMap(String filePath){
		File f;
		
		if (filePath != null) 
			f = new File(filePath);
		else
			f = new File(DEFAULT_PATH); 
		
		if (!(f.isFile() && f.canRead())){
			_profiles = new HashMap<>();
			return;
		}
		
		try {
			ObjectMapper oMapper = new ObjectMapper();
			
			Map<String, Profile> profiles = oMapper.readValue(f, new TypeReference<Map<String, Profile>>() {});
			
			_profiles = profiles;
			
		} catch (IOException e) {
			_profiles = new HashMap<>();
		}
	}
	
	/**
	 * Adds a profile to the profiles hashmap
	 * @param profileName the key to use for insertion
	 * @param p the profile to insert
	 * @return true if it was possible to create a profile of the given name, and false otherwise
	 */
	public boolean addProfile(String profileName, Profile p){
		if (_profiles.containsKey(profileName))
			return false;
		
		_profiles.put(profileName, p);
		return true;
	}
	
	/**
	 * Deletes a profile from the hashmap
	 * @param profileName the profile to delete
	 */
	public void deleteProfile(String profileName){
		_profiles.remove(profileName);
	}

	/**
	 * Gets a profile from the hashmap
	 * @param profileName the key for the profile to retreive
	 * @return the Profile mapped to by profileName
	 */
	public Profile getProfile(String profileName){
		return _profiles.get(profileName);
	}
	
	/**
	 * Getter for the profiles attribute
	 * @return _profiles
	 */
	public Map<String, Profile> getProfiles(){
		return _profiles;
	}
}
