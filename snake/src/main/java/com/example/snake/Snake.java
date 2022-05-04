package com.example.snake;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.*;

public class Snake extends Application {

    private MySquare[] tabS;
    private int positionP,speed,score;
    private List<Integer> positionS;
    private String oriantation;
    private List<Integer> shit;
    private Timer t;
    public class tt extends TimerTask {

        private List<Integer> timeEach,ancianPositionS;
        private int passe;


        public tt(){
            this.timeEach=new ArrayList<>();
        }

        @Override
        public void run() {
            try {
                if(!oriantation.equals("")){
                    this.passe=positionS.get(positionS.size()-1);
                    this.ancianPositionS=new ArrayList<>();
                    this.ancianPositionS.addAll(positionS);
                    deplace();
                    int teteF = positionS.get(0);
                    switch (oriantation) {
                        case "haut" -> teteF -= 25;
                        case "base" -> teteF += 25;
                        case "gauche" -> teteF -= 1;
                        case "droit" -> teteF += 1;
                    }
                    if(teteF%25==0&&positionS.get(0)%25==24||teteF%25==24&&positionS.get(0)%25==0||positionS.contains(teteF)||shit.contains(teteF))throw new ArrayIndexOutOfBoundsException();
                    else positionS.set(0,teteF);
                    ColoS();
                }
                //
                if(positionP==positionS.get(0)){
                    hasetBody();
                    score++;
                    this.timeEach.add(0);
                    while (positionS.contains(positionP)||shit.contains(positionP))positionP=(int)(Math.random()*25*20);
                    tabS[positionP].setFill(Color.RED);
                }
                for(int i=0;i<this.timeEach.size();i++){
                    this.timeEach.set(i,this.timeEach.get(i)+200);
                    if(this.timeEach.get(i)>=1000*3){
                        shit.add(passe);
                        this.timeEach.remove(i);
                        i--;
                    }
                }
                if(shit.contains(passe))tabS[passe].setFill(Color.BLACK);
                else tabS[passe].setFill(Color.BLUE);
            }catch (ArrayIndexOutOfBoundsException e){
                System.out.println("Your score is "+score);
                for(int i:this.ancianPositionS)tabS[i].setFill(Color.WHITE);
                this.cancel();
            }
        }
    }

    @Override
    public void start(Stage stage){
        TilePane root=new TilePane();
        root.setMinWidth(21*25);

        this.positionS= new ArrayList<>();
        this.positionS.add((int)(Math.random()*25*20));
        this.tabS=new MySquare[20*25];
        for(int i=0;i<25*20;i++){
            this.tabS[i]=new MySquare();
        }
        for(int i=0;i<2;i++){
            hasetBody();
        }
        this.ColoS();
        this.positionP=(int)(Math.random()*25*20);
        while (this.positionS.contains(this.positionP))this.positionP=(int)(Math.random()*25*20);
        this.tabS[this.positionP].setFill(Color.RED);
        root.getChildren().addAll(this.tabS);

        Scene scene=new Scene(root);
        scene.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getText()){
                case "z":if(!Objects.equals(oriantation, "haut") &&positionS.get(1)!=positionS.get(0)-25)oriantation="haut";break;
                case "s":if(!Objects.equals(oriantation, "haut") &&positionS.get(1)!=positionS.get(0)+25)oriantation="base";break;
                case "q":if(!Objects.equals(oriantation, "gauche") &&positionS.get(1)!=positionS.get(0)-1)oriantation="gauche";break;
                case "d":if(!Objects.equals(oriantation, "gauche") &&positionS.get(1)!=positionS.get(0)+1)oriantation="droit";break;
            }
        });
        stage.setTitle("Sneke");
        stage.setScene(scene);
        this.oriantation="";
        this.shit=new ArrayList<>();
        this.speed=200;
        this.score=0;
        t=new Timer();
        t.schedule(new tt(), 0, this.speed);
        stage.show();
    }


    private void deplace(){
        for(int i=1;i<this.positionS.size();i++)this.positionS.set(this.positionS.size()-i,this.positionS.get(this.positionS.size()-i-1));
    }

    private void ColoS(){
        this.tabS[this.positionS.get(0)].setFill(Color.ORANGE);
        for(int i=1;i<this.positionS.size();i++)this.tabS[this.positionS.get(i)].setFill(Color.YELLOW);
    }

    private void hasetBody(){
        int dernier=this.positionS.size() - 1;
        List<Integer> can=new ArrayList<>();
        if(this.positionS.get(dernier)%25<24)can.add(this.positionS.get(dernier)+1);
        if(this.positionS.get(dernier)%25>0)can.add(this.positionS.get(dernier)-1);
        if(this.positionS.get(dernier)+25<500)can.add(this.positionS.get(dernier)+25);
        if(this.positionS.get(dernier)-25>=0)can.add(this.positionS.get(dernier)-25);
        if(dernier>0)can.remove(this.positionS.get(dernier-1));
        int positionB=(int)(Math.random()*10)*can.size()/10;
        if(can.size()>0){
            positionB=can.get(positionB);
            this.positionS.add(positionB);
        }
    }


}

class MySquare extends Rectangle{

    public MySquare(){
        super(20,20, Color.BLUE);
        this.setStrokeWidth(1);
        this.setStroke(Color.BLACK);
    }

}