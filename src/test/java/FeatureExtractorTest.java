import com.finbourne.features.reporter.DuplicateFeatureException;
import com.finbourne.features.reporter.FeatureExtractor;
import com.finbourne.features.reporter.FeatureFileWriter;
import com.finbourne.features.reporter.NullFeatureValueException;
import org.apache.commons.io.Charsets;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

public class FeatureExtractorTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void checkIfWriterWritesDummyFeaturesCorrectly() throws ClassNotFoundException, IOException, URISyntaxException, DuplicateFeatureException, NullFeatureValueException {
        FeatureExtractor featureExtractor = new FeatureExtractor();
        FeatureFileWriter featureFileWriter = new FeatureFileWriter();
        File file = File.createTempFile("features", ".txt");
        String filePath = file.toString();
        file.deleteOnExit();

        List<String> annotations = featureExtractor.getAnnotations("com.finbourne.examples.valid");
        String annotationsFromMethod = String.join("\n", annotations);


        featureFileWriter.writeToFile(annotationsFromMethod, filePath);
        List<String> lines = Files.readAllLines(Paths.get(filePath), Charsets.UTF_8);
        String annotationsFromFile = String.join("\n", lines);

        assertNotEquals(annotationsFromFile.length(), 0);
        assertThat(annotationsFromMethod, equalTo(annotationsFromFile));
    }

    @Test
    public void getValidAnnotations() throws ClassNotFoundException, IOException, URISyntaxException, DuplicateFeatureException, NullFeatureValueException {
        FeatureExtractor featureExtractor = new FeatureExtractor();

        List<String> annotations = featureExtractor.getAnnotations("com.finbourne.examples.valid");

        assertThat(annotations.size(), equalTo(2));
        assertThat(annotations, hasItems("F1", "F2"));
    }

    @Test
    public void throwErrorOnDuplicateAnnotations() throws ClassNotFoundException, IOException, NullFeatureValueException, DuplicateFeatureException, URISyntaxException {
        FeatureExtractor featureExtractor = new FeatureExtractor();

        thrown.expect(DuplicateFeatureException.class);
        List<String> annotations = featureExtractor.getAnnotations("com.finbourne.examples.duplicates");

    }

    @Test
    public void throwErrorOnEmptyStringAnnotations() throws ClassNotFoundException, IOException, NullFeatureValueException, DuplicateFeatureException, URISyntaxException {
        FeatureExtractor featureExtractor = new FeatureExtractor();

        thrown.expect(NullFeatureValueException.class);
        List<String> annotations = featureExtractor.getAnnotations("com.finbourne.examples.empties");

    }

    @Test
    public void throwErrorOnNoInputAnnotations() throws ClassNotFoundException, IOException, NullFeatureValueException, DuplicateFeatureException, URISyntaxException {
        FeatureExtractor featureExtractor = new FeatureExtractor();

        thrown.expect(NullFeatureValueException.class);
        List<String> annotations = featureExtractor.getAnnotations("com.finbourne.examples.noinput");
    }

    @Test
    public void returnEmptyListWhenMethodsNotAnnotated() throws ClassNotFoundException, IOException, NullFeatureValueException, DuplicateFeatureException, URISyntaxException {
        FeatureExtractor featureExtractor = new FeatureExtractor();

        List<String> annotations = featureExtractor.getAnnotations("com.finbourne.examples.notannotated");

        assertThat(annotations.size(), equalTo(0));
    }
}
