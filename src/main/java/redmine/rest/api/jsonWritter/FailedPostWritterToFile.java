package redmine.rest.api.jsonWritter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import redmine.rest.api.model.jira.JiraWorkLog;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Slf4j
@NoArgsConstructor
public class FailedPostWritterToFile {

    public void logWrongJSONToFile(JiraWorkLog workLog, Exception e) {
        File incorrectJSONFile = new File(getResourcesAbsolutePath());
        try {
            if (incorrectJSONFile.createNewFile()) {
                initializeIncorrectJSONFile(incorrectJSONFile);
                log.info("Incorrent JSON object file is created");
            }
            FileWriter writer = new FileWriter(incorrectJSONFile, true);
            String json = createWorkLogJSONWithErrorParameter(workLog, e);
            writer.write(json + "\n]");
            writer.flush();
            writer.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void logWrongJSONToFile(ArrayList<JiraWorkLog> workLogs, ArrayList<Exception> e) {
        File incorrectJSONFile = new File(getResourcesAbsolutePath());
        try {
            if (incorrectJSONFile.createNewFile()) {
                initializeIncorrectJSONFile(incorrectJSONFile);
                log.info("Incorrent JSON object file is created");
            }
            FileWriter writer = new FileWriter(incorrectJSONFile, true);
            for (int i = 0; i < workLogs.size(); i++) {
                String json = createWorkLogJSONWithErrorParameter(workLogs.get(i), e.get(i));
                if (i != workLogs.size() - 1) {
                    writer.write(json + ",\n");
                } else {
                    writer.write(json + "\n]\n}");
                }
                writer.flush();
            }
            writer.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private String getResourcesAbsolutePath() {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        File currentDirFile = new File("");
        String helper = currentDirFile.getAbsolutePath();
        String currentDir = helper + "/src/main/resources/json/incorrect_" + timeStamp + ".json";
        return currentDir;
    }

    private void initializeIncorrectJSONFile(File file) {
        try {
            FileWriter writer = new FileWriter(file);
            writer.write("{\n\"results\": [\n");
            writer.flush();
            writer.close();
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
