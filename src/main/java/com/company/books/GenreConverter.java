package com.company.books;

        import javax.persistence.AttributeConverter;
        import javax.persistence.Converter;

@Converter(autoApply = true)
public class GenreConverter implements AttributeConverter<Genre, String> {

    @Override
    public String convertToDatabaseColumn(Genre genre) {
        return (genre != null)? genre.getGenre() : null;
    }


    @Override
    public Genre convertToEntityAttribute(String genre) {
        return (genre != null)? Genre.fromGenre(genre) : null;
    }
}
