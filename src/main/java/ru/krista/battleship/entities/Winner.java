package ru.krista.battleship.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import java.util.Date;

@Entity
@NamedQuery(name = "Winner.getAll", query = "SELECT w from Winner w")
public class Winner {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private Date startDate;
    private Date finishDate;
    private String result;

    public Winner() {
    }

    public Winner(String name, String result, Date startDate, Date finishDate) {
        this.name = name;
        this.startDate = startDate;
        this.result = result;
        this.finishDate = finishDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Winner{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", finishDate=" + finishDate +
                ", result='" + result + '\'' +
                '}';
    }
}
