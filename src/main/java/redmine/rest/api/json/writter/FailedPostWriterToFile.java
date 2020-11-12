package redmine.rest.api.json.writter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import redmine.rest.api.json.model.FailedPost;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@NoArgsConstructor
public class FailedPostWriterToFile {

    private static final String FILE_CREATION_MESSAGE = "Incorrent JSON object file is created";

    public void logWrongJSONToFile(FailedPost failedPost) {
        File incorrectJSONFile = new File(getResourcesAbsolutePath());

        try (FileWriter writer = new FileWriter(incorrectJSONFile, true)) {
            initializeIncorrectJSONFile(incorrectJSONFile);
            log.info(FILE_CREATION_MESSAGE);

            String json = createWorkLogJSONWithErrorParameter(failedPost);
            writer.write(json + "\n]");
            writer.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void logWrongJSONToFile(List<FailedPost> failedPosts) {
        File incorrectJSONFile = new File(getResourcesAbsolutePath());

        try (FileWriter writer = new FileWriter(incorrectJSONFile, true)) {
            initializeIncorrectJSONFile(incorrectJSONFile);
            log.info(FILE_CREATION_MESSAGE);

            for (int i = 0; i < failedPosts.size(); i++) {
                String json = createWorkLogJSONWithErrorParameter(failedPosts.get(i));
                if (i != failedPosts.size() - 1) {
                    writer.write(json + ",\n");
                } else {
                    writer.write(json + "\n]\n}");
                }
            }
            writer.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private String getResourcesAbsolutePath() {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        File currentDirFile = new File("");
        String helper = currentDirFile.getAbsolutePath();
        return helper + "/src/main/resources/json/incorrect_" + timeStamp + ".json";
    }

    private void initializeIncorrectJSONFile(File file) {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("{\n\"results\": [\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String createWorkLogJSONWithErrorParameter(FailedPost failedPost) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(failedPost);
    }

}
