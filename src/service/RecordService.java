package service;

import dao.PersonalRecordDAO;
import model.PersonalRecord;

import java.util.List;

public class RecordService {

    private PersonalRecordDAO recordDAO = new PersonalRecordDAO();

    // Add record
    public void addRecord(PersonalRecord record) {

        if (record.getUserId() <= 0 || record.getExerciseId() <= 0) {
            System.out.println("Invalid record data!");
            return;
        }

        recordDAO.addRecord(record);
    }

    // Get records of user
    public List<PersonalRecord> getUserRecords(int userId) {
        return recordDAO.getRecordsByUser(userId);
    }

    // Delete record
    public void deleteRecord(int recordId) {
        recordDAO.deleteRecord(recordId);
    }
}