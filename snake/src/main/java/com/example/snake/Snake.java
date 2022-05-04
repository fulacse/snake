package com.example.snake;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.*;

public class Snake extends Application {

    private MySquare[] tabS;
    private int positionP;
    private List<Integer> positionS;
    private String oriantation;
    class tt extends TimerTask {

        @Override
        public void run() {
            try {
                if(!oriantation.equals("")){
                    int passe=positionS.get(positionS.size()-1);
                    deplace();
                    int teteF = positionS.get(0);
                    switch (oriantation) {
                        case "haut" -> teteF -= 25;
                        case "base" -> teteF += 25;
                        case "gauche" -> teteF -= 1;
                        case "droit" -> teteF += 1;
                    }
                    if(teteF%25==0&&positionS.get(0)%25==24||teteF%25==24&&positionS.get(0)%25==0||positionS.contains(teteF))throw new ArrayIndexOutOfBoundsException();
                    else positionS.set(0,teteF);
                    ColoS();
                    tabS[passe].setFill(Color.BLUE);
                }
            }catch (ArrayIndexOutOfBoundsException e){
                System.out.println("You lose!!!");
                this.cancel();
            }
            if(positionP==positionS.get(0)){
                hasetBody();
                while (positionS.contains(positionP))positionP=(int)(Math.random()*25*20);
                tabS[positionP].setFill(Color.RED);
            }
        }
    }

    @Override
    public void start(Stage stage){
        TilePane root=new TilePane();
        root.setMinWidth(25*21);

        this.positionP=(int)(Math.random()*25*20);
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
        Timer t=new Timer();
        t.schedule(new tt(), 0, 200);
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
        if(dernier%25!=24&&this.positionS.get(dernier)+1<500)can.add(this.positionS.get(dernier)+1);
        if(dernier%25!=0&&this.positionS.get(dernier)-1>=0)can.add(this.positionS.get(dernier)-1);
        if(dernier+25<20*25&&this.positionS.get(dernier)+25<500)can.add(this.positionS.get(dernier)+25);
        if(dernier-25>=0&&this.positionS.get(dernier)-25>=0)can.add(this.positionS.get(dernier)-25);
        if(dernier>0)can.remove((Object)this.positionS.get(dernier-1));
        int positionB=(int)(Math.random()*20)/10;
        positionB=can.get(positionB);
        this.positionS.add(positionB);
    }

}

class MySquare extends Rectangle{

    public MySquare(){
        super(20,20, Color.BLUE);
        this.setStrokeWidth(1);
        this.setStroke(Color.BLACK);
    }

}