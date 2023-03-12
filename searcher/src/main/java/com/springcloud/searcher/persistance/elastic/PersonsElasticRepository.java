package com.springcloud.searcher.persistance.elastic;

import com.springcloud.searcher.api.PersonsSearchHandler;
import com.springcloud.searcher.ingestion.PersonRepository;
import com.springcloud.searcher.model.Person;
import com.springcloud.searcher.model.Property;
import com.springcloud.searcher.model.PropertyType;
import lombok.RequiredArgsConstructor;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.data.elasticsearch.core.DocumentOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchOperations;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.nestedQuery;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.elasticsearch.index.query.QueryBuilders.termsQuery;

@Repository
@RequiredArgsConstructor
public class PersonsElasticRepository implements PersonRepository, PersonsSearchHandler {

  private final SearchOperations searchOperations;
  private final DocumentOperations documentOperations;

  @Override
  public Person findById(String id) {
    return documentOperations.get(id, Person.class);
  }

  @Override
  public void save(Person person) {
    documentOperations.save(person);
  }

  @Override
  public Person findByPropertyId(PropertyType type, String id) {
    NestedQueryBuilder nestedQuery  = nestedQuery(
        Person.Fields.property,
        boolQuery()
            .filter(termsQuery(Person.Fields.property + "." + Property.Fields.type, type.name()))
            .filter(termsQuery(Person.Fields.property + "." + Property.Fields.propertyId, id)),
        ScoreMode.None
    );
    return searchOperations.search(new NativeSearchQuery(nestedQuery), Person.class)
        .getSearchHits()
        .stream()
        .map(SearchHit::getContent)
        .findFirst()
        .orElse(null);
  }

  @Override
  public List<Person> search(String requestString) {
    Person person = documentOperations.get(requestString, Person.class);
    if (person != null) {
      return List.of(person);
    }
    SearchHits<Person> persons = searchOperations.search(queryForDocument(requestString), Person.class);
    if (persons.hasSearchHits()) {
      return persons.getSearchHits()
          .stream()
          .map(SearchHit::getContent)
          .toList();
    }
    return searchOperations.search(queryForText(requestString), Person.class)
        .getSearchHits()
        .stream()
        .map(SearchHit::getContent)
        .toList();
  }

  private Query queryForDocument(String request) {
    return new NativeSearchQuery(nestedQuery(
        Person.Fields.property,
        termsQuery(
            Person.Fields.property + "." + Property.Fields.document,
            request),
        ScoreMode.None)
    );
  }

  private Query queryForText(String request) {
    final QueryStringQueryBuilder personQuery = queryStringQuery(request)
        .fields(Map.of(
            Person.Fields.fullName, 2f,
            Person.Fields.address, 1f
        ));

    final NestedQueryBuilder propertyQuery = nestedQuery(
        Person.Fields.property,
        queryStringQuery(request)
            .fields(Map.of(
                Person.Fields.property + "." + Property.Fields.description,
                1f
            )), ScoreMode.None);

    return new NativeSearchQuery(boolQuery()
        .should(personQuery)
        .should(propertyQuery));
  }
}
