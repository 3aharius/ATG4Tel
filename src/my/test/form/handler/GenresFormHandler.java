package my.test.form.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;

import my.test.form.model.Genre;
import atg.droplet.GenericFormHandler;
import atg.repository.MutableRepository;
import atg.repository.MutableRepositoryItem;
import atg.repository.Query;
import atg.repository.QueryBuilder;
import atg.repository.QueryExpression;
import atg.repository.RepositoryException;
import atg.repository.RepositoryItem;
import atg.repository.RepositoryView;
import atg.servlet.DynamoHttpServletRequest;
import atg.servlet.DynamoHttpServletResponse;

public class GenresFormHandler extends GenericFormHandler {

	private String firstName;
	private String lastName;
	private String email;
	private String[] selectedGenres;
	private String successURL;
	
	private MutableRepository repository;
	
	public boolean  handlSubscrbe(DynamoHttpServletRequest request,
		     DynamoHttpServletResponse response)throws IOException, ServletException{
		try {
			MutableRepositoryItem userItem = getRepository().createItem("user");
			userItem.setPropertyValue("firstName", firstName);
			userItem.setPropertyValue("lastName", lastName);
			userItem.setPropertyValue("email", email);
			userItem.setPropertyValue("suscriptionDate", new Date());
			
			RepositoryView genreView = getRepository().getView("genre");
		    QueryBuilder genreBuilder = genreView.getQueryBuilder();
			
		    QueryExpression idPropExpression = genreBuilder.createPropertyQueryExpression("id");
		    QueryExpression idValExpression = genreBuilder.createConstantQueryExpression(selectedGenres);
		    Query genreQuery = genreBuilder.createIncludesQuery(idValExpression, idPropExpression);
		    Set userGenres = (Set) userItem.getPropertyValue("genres");
		    for(RepositoryItem genreItem: genreView.executeQuery(genreQuery)) {
		    	userGenres.add(genreItem);
		    }
			RepositoryItem item = getRepository().addItem(userItem);
			response.sendLocalRedirect(successURL + "?uid=" + item.getPropertyValue("id"), request);
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
		return false;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public MutableRepository getRepository() {
		return repository;
	}

	public void setRepository(MutableRepository repository) {
		this.repository = repository;
	}
		
	public String[] getSelectedGenres() {
		return selectedGenres;
	}

	public void setSelectedGenres(String[] selectedGenres) {
		this.selectedGenres = selectedGenres;
	}

	public String getSuccessURL() {
		return successURL;
	}

	public void setSuccessURL(String successURL) {
		this.successURL = successURL;
	}

	public List<Genre> getGenres() {
		List<Genre> genres = new ArrayList<>();
		try {
			RepositoryView view = getRepository().getView("genre");
		    QueryBuilder genreBuilder = view.getQueryBuilder();
		    Query query = genreBuilder.createUnconstrainedQuery();
		    RepositoryItem[] items = view.executeQuery(query);
		    for(RepositoryItem item: items) {
		    	genres.add(new Genre(
		    			(Long) item.getPropertyValue("id"),
		    			(String) item.getPropertyValue("title"),
		    			(String) item.getPropertyValue("description")));
		    }
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
        return genres;
	}
	
	
	
}
