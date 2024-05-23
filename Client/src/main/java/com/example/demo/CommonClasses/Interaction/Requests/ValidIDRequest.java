package com.example.demo.CommonClasses.Interaction.Requests;

public class ValidIDRequest extends AutentificationRequest{

    private Long id;
    public ValidIDRequest(Long id) {
        super();
        this.id = id;
    }

    public ValidIDRequest() {
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean isCorrect() {
        return super.isCorrect()&&id!=null&&id>=0;
    }
}
