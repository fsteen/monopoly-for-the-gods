package edu.brown.cs32.MFTG.networking;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.brown.cs32.MFTG.tournament.Profile;

public class ProfileManager {
	private final String DEFAULT_PATH = ".profiles.mf";
	private String _filePath;
	private final ObjectMapper _oMapper;

	Map<String, Profile> _profiles;

	public ProfileManager(){
		_filePath = DEFAULT_PATH;
		_oMapper = new ObjectMapper();
		buildProfileMap();
	}

	public ProfileManager(String filePath){
		_filePath = filePath;
		_oMapper = new ObjectMapper();
		buildProfileMap();
	}

	/**
	 * Builds the profile map from the given input file
	 */
	private void buildProfileMap(){
		File f = new File(_filePath);

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
	 * Saves the current profile information to disk
	 * @return true if the profile could be saved, and false otherwise
	 */
	public boolean saveProfiles(){
		File f = new File(_filePath);

		// make sure that the file is either writable or nonexistent
		if (f.isFile() && !f.canWrite())
			return false;
		
		// if f exists, make sure that it's a file
		if (f.exists() && !f.isFile())
			return false;
		
		// attempt to write the profiles to file
		try (BufferedWriter bWriter = new BufferedWriter(new FileWriter(f))){
			String json = _oMapper.writeValueAsString(_profiles);
			bWriter.write(json);
			bWriter.flush();
			return true;
		} catch (IOException e) {
			return false;
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
		return saveProfiles();
	}

	/**
	 * Deletes a profile from the hashmap
	 * @param profileName the profile to delete
	 * @return true if it was possible to write the deletion to disk, and false otherwise
	 */
	public boolean deleteProfile(String profileName){
		_profiles.remove(profileName);
		return saveProfiles();
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
	 * Returns a set of the names of all the profiles
	 * @return a set of the names of all the profiles
	 */
	public Set<String> getProfileNames(){
		return _profiles.keySet();
	}

	/**
	 * 
	 * @param profile
	 * @return set of player names
	 */
	public Set<String> getPlayerNames(String profile){
		return _profiles.get(profile).getPlayerNames();
	}

	/**
	 * 
	 * @param profile
	 * @return set of settings names
	 */
	public Set<String> getSettingsNames(String profile){
		return _profiles.get(profile).getSettingsNames();
	}

	/**
	 * Returns the number of profiles
	 * @return the number of profiles
	 */
	public int numProfiles(){
		return _profiles.size();
	}
}
