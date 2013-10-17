package net.fortytwo.extendo.brain.rdf.types;

import net.fortytwo.extendo.brain.Atom;
import net.fortytwo.extendo.brain.rdf.BottomUpType;
import net.fortytwo.extendo.brain.rdf.Field;
import net.fortytwo.extendo.brain.rdf.Mapper;
import net.fortytwo.extendo.brain.rdf.MappingContext;
import net.fortytwo.extendo.brain.rdf.vocab.DCTerms;
import net.fortytwo.extendo.brain.rdf.vocab.FOAF;
import org.openrdf.model.URI;
import org.openrdf.model.ValueFactory;
import org.openrdf.rio.RDFHandler;
import org.openrdf.rio.RDFHandlerException;

import java.util.regex.Pattern;

/**
 * @author Joshua Shinavier (http://fortytwo.net)
 */
public class ArticleOrBook extends BottomUpType {
    public static final ArticleOrBook INSTANCE = new ArticleOrBook();

    private Field[] fields = null;

    private ArticleOrBook() {
    }

    public Field[] getFields() {
        if (null == fields) {
            fields = new Field[]{
                    new Field(true, null, ISBN.INSTANCE, null, new ISBNMapper()),
                    new Field(false, null, RFID.INSTANCE, null, new RFIDMapper()),
                    new Field(true, null, BibtexReference.INSTANCE, null, new BibtexReferenceMapper()),
                    new Field(true, Pattern.compile("the authors of .+"), OpenCollection.INSTANCE, Person.INSTANCE, new AuthorCollectionMapper()),
                    // "some notes from" is not unique, since movies, conversations, and other types also have this field
                    new Field(false, Pattern.compile("some notes from .+"), OpenCollection.INSTANCE, null, new SomeNotesFromTheDocumentMapper())};
        }

        return fields;
    }

    public Pattern getValueRegex() {
        // note: currently excludes names which begin with special characters (e.g. Chinese or certain European names)
        return Pattern.compile("[A-Z].+");
    }

    public boolean additionalConstraintsSatisfied(final String value) {
        return true;
    }

    public boolean aliasRequired() {
        return false;
    }

    public boolean childrenRequired() {
        return false;
    }

    public URI translateToRDF(final Atom a,
                               final ValueFactory vf,
                               final RDFHandler handler) throws RDFHandlerException {
        // TODO: a more specific type than foaf:Document may be appropriate (WebPage also uses foaf:Document)
        URI self = translateTypeAndAlias(a, vf, handler, FOAF.DOCUMENT);

        handler.handleStatement(vf.createStatement(self, DCTerms.TITLE, vf.createLiteral(a.getValue())));

        return self;
    }

    private class ISBNMapper implements Mapper {
        public void mapToRDF(Atom child, MappingContext context) throws RDFHandlerException {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }

    private class RFIDMapper implements Mapper {
        public void mapToRDF(Atom child, MappingContext context) throws RDFHandlerException {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }

    private class BibtexReferenceMapper implements Mapper {
        public void mapToRDF(Atom child, MappingContext context) throws RDFHandlerException {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }

    private class AuthorCollectionMapper implements Mapper {
        public void mapToRDF(Atom child, MappingContext context) throws RDFHandlerException {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }

    private class SomeNotesFromTheDocumentMapper implements Mapper {
        public void mapToRDF(Atom child, MappingContext context) throws RDFHandlerException {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }
}
