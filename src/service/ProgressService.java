package service;

import dao.BodyProgressDAO;
import model.BodyProgress;

import java.util.List;

public class ProgressService {

    private BodyProgressDAO bodyProgressDAO = new BodyProgressDAO();

    //ADD PROG
    public void addProgress(BodyProgress progress) {

        if (progress.getUserId() <= 0) {
            System.out.println("Invalid user ID!");
            return;
        }

        bodyProgressDAO.addProgress(progress);
    }

    //GET PROG
    public List<BodyProgress> getUserProgress(int userId) {
        return bodyProgressDAO.getProgressByUser(userId);
    }

    //DELETE PROG
    public void deleteProgress(int progressId) {
        bodyProgressDAO.deleteProgress(progressId);
    }
}