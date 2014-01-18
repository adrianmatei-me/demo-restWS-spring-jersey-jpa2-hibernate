package org.codingpedia.demo.rest.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.codingpedia.demo.rest.dao.PodcastDao;
import org.codingpedia.demo.rest.entities.Podcast;

public class PodcastDaoJPA2Impl implements PodcastDao {

	@PersistenceContext
	private EntityManager em;
	
	public List<Podcast> getPodcasts() {
		
		String qlString = "SELECT p FROM Podcast p";
		TypedQuery<Podcast> query = em.createQuery(qlString, Podcast.class);		

		return query.getResultList();
	}

	public Podcast getPodcastById(Long id) {
		
		try {
			String qlString = "SELECT p FROM Podcast p WHERE p.id = ?1";
			TypedQuery<Podcast> query = em.createQuery(qlString, Podcast.class);		
			query.setParameter(1, id);

			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Long deletePodcastById(Long id) {
		
		Podcast podcast = em.find(Podcast.class, id);
		em.remove(podcast);
		
		return 1L;
	}

	public Long createPodcast(Podcast podcast) {
		
		em.persist(podcast);
		em.flush();//force insert to receive the id of the podcast
		
		return podcast.getId();
	}

	public int updatePodcast(Podcast podcast) {
		
		em.merge(podcast);
		
		return 1; 
	}

	public void deletePodcasts() {
		Query query = em.createNativeQuery("TRUNCATE TABLE podcasts");		
		query.executeUpdate();
	}

}
