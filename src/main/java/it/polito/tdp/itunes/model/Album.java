package it.polito.tdp.itunes.model;

import java.util.*;

public class Album implements Comparable<Album>{
	private Integer albumId;
	private String title;
	public void setTracksIds(Set<Integer> tracksIds) {
		this.tracksIds = tracksIds;
	}

	public Set<Integer> getTracksIds() {
		return tracksIds;
	}

	private Double durata;
	
	Set<Integer> tracksIds;
	
	public Album(Integer albumId, String title, Double durata) {
		super();
		this.albumId = albumId;
		this.title = title;
		this.durata = durata;
		tracksIds = new HashSet<>();
	}

	public Integer getAlbumId() {
		return albumId;
	}

	public void setAlbumId(Integer albumId) {
		this.albumId = albumId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Double getDurata() {
		return durata;
	}

	public void setDurata(Double durata) {
		this.durata = durata;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((albumId == null) ? 0 : albumId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Album other = (Album) obj;
		if (albumId == null) {
			if (other.albumId != null)
				return false;
		} else if (!albumId.equals(other.albumId))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return title;
	}

	@Override
	public int compareTo(Album o) {
		return this.title.compareTo(o.title);
	}
	
	
	
}
