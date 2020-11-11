package redmine.rest.api.json_writter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import redmine.rest.api.model.jira.JiraWorkLog;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@NoArgsConstructor
public class FailedPostWritterToFile {

    private static final String FILE_CREATION_MESSAGE = "Incorrent JSON object file is created";

    public void logWrongJSONToFile(JiraWorkLog workLog, Exception e) {
        File incorrectJSONFile = new File(getResourcesAbsolutePath());

        try (FileWriter writer = new FileWriter(incorrectJSONFile, true)) {
            initializeIncorrectJSONFile(incorrectJSONFile);
            log.info(FILE_CREATION_MESSAGE);

            String json = createWorkLogJSONWithErrorParameter(workLog, e);
            writer.write(json + "\n]");
            writer.flush();

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void logWrongJSONToFile(List<JiraWorkLog> workLogs, List<Exception> e) {
        File incorrectJSONFile = new File(getResourcesAbsolutePath());

        try (FileWriter writer = new FileWriter(incorrectJSONFile, true)) {
            log.info(FILE_CREATION_MESSAGE);
            initializeIncorrectJSONFile(incorrectJSONFile);
            for (int i = 0; i < workLogs.size(); i++) {
                String json = createWorkLogJSONWithErrorParameter(workLogs.get(i), e.get(i));
                if (i != workLogs.size() - 1) {
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

    private String createWorkLogJSONWithErrorParameter(JiraWorkLog log, Exception e) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(log);
        String exceptionJSON = ",\"exception\":\"" + e.getMessage() + "\"";
        return new StringBuilder(json).insert(json.length() - 1, exceptionJSON).toString();
    }

}
