package service;

import dao.PersonalRecordDAO;
import model.PersonalRecord;

import java.util.List;

public class RecordService {

    private PersonalRecordDAO recordDAO = new PersonalRecordDAO();

    //ADD RECORD
    public void addRecord(PersonalRecord record) {

        if (record.getUserId() <= 0 || record.getExerciseId() <= 0) {
            System.out.println("Invalid record data!");
            return;
        }

        recordDAO.addRecord(record);
    }

    //GIET RECORD
    public List<PersonalRecord> getUserRecords(int userId) {
        return recordDAO.getRecordsByUser(userId);
    }

    //DELETE RECORD
    public void deleteRecord(int recordId) {
        recordDAO.deleteRecord(recordId);
    }
}