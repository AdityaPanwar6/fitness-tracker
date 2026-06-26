package service;

import dao.BodyProgressDAO;
import model.BodyProgress;

import java.util.List;

public class ProgressService {

    private BodyProgressDAO bodyProgressDAO = new BodyProgressDAO();

    // Add progress
    public void addProgress(BodyProgress progress) {

        if (progress.getUserId() <= 0) {
            System.out.println("Invalid user ID!");
            return;
        }

        bodyProgressDAO.addProgress(progress);
    }

    // Get progress of user
    public List<BodyProgress> getUserProgress(int userId) {
        return bodyProgressDAO.getProgressByUser(userId);
    }

    // Delete progress
    public void deleteProgress(int progressId) {
        bodyProgressDAO.deleteProgress(progressId);
    }
}