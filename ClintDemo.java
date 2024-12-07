package com.klef.jfsd;

import org.hibernate.criterion.Projections;

public class ClintDemo {
    public static void main(String[] args) {
        // Configure Hibernate
        SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            // Insertion
            tx = session.beginTransaction();
            Project project1 = new Project();
            project1.setProjectName("AI Research");
            project1.setDuration(12);
            project1.setBudget(1500000);
            project1.setTeamLead("Dr. John Doe");

            Project project2 = new Project();
            project2.setProjectName("Cloud Migration");
            project2.setDuration(6);
            project2.setBudget(750000);
            project2.setTeamLead("Ms. Jane Smith");

            session.save(project1);
            session.save(project2);
            tx.commit();

            // Aggregate Functions on Budget
            Criteria criteria = session.createCriteria(Project.class);
            
            criteria.setProjection(Projections.rowCount());
            System.out.println("Count of Projects: " + criteria.uniqueResult());

            criteria.setProjection(Projections.max("budget"));
            System.out.println("Maximum Budget: " + criteria.uniqueResult());

            criteria.setProjection(Projections.min("budget"));
            System.out.println("Minimum Budget: " + criteria.uniqueResult());

            criteria.setProjection(Projections.sum("budget"));
            System.out.println("Total Budget: " + criteria.uniqueResult());

            criteria.setProjection(Projections.avg("budget"));
            System.out.println("Average Budget: " + criteria.uniqueResult());

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            factory.close();
        }
    }
}
