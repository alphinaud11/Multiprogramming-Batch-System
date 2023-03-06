public class Process extends Thread {

    public int processID;
    ProcessState status = ProcessState.New;
    static Mutex mutRead = new Mutex();
    static Mutex mutWrite = new Mutex();
    static Mutex mutPrint = new Mutex();
    static Mutex mutTake = new Mutex();


    public Process(int m) {
        processID = m;
    }
    @Override
    public void run() {

        switch(processID)
        {
            case 1:process1();break;
            case 2:process2();break;
            case 3:process3();break;
            case 4:process4();break;
            case 5:process5();break;
        }

    }

    private void process1() {

        mutPrint.semWait(this);
        mutTake.semWait(this);
        OperatingSystem.printText("Enter File Name: ");
        String fileName = OperatingSystem.TakeInput();
        mutTake.semSignal(this);
        mutRead.semWait(this);
        String data = OperatingSystem.readFile(fileName);
        mutRead.semSignal(this);
        OperatingSystem.printText(data);
        mutPrint.semSignal(this);

        setProcessState(ProcessState.Terminated);
    }

    private void process2() {

        mutPrint.semWait(this);
        mutTake.semWait(this);
        OperatingSystem.printText("Enter File Name: ");
        String filename= OperatingSystem.TakeInput();
        OperatingSystem.printText("Enter Data: ");
        String data= OperatingSystem.TakeInput();
        mutPrint.semSignal(this);
        mutTake.semSignal(this);
        mutWrite.semWait(this);
        OperatingSystem.writefile(filename,data);
        mutWrite.semSignal(this);

        setProcessState(ProcessState.Terminated);
    }

    private void process3() {

        mutPrint.semWait(this);
        int x=0;
        while (x<301)
        {
            OperatingSystem.printText(x+"\n");
            x++;
        }
        mutPrint.semSignal(this);

        setProcessState(ProcessState.Terminated);
    }

    private void process4() {

        mutPrint.semWait(this);
        int x=500;
        while (x<1001)
        {
            OperatingSystem.printText(x+"\n");
            x++;
        }
        mutPrint.semSignal(this);

        setProcessState(ProcessState.Terminated);
    }

    private void process5() {

        mutPrint.semWait(this);
        mutTake.semWait(this);
        OperatingSystem.printText("Enter LowerBound: ");
        String lower= OperatingSystem.TakeInput();
        OperatingSystem.printText("Enter UpperBound: ");
        String upper= OperatingSystem.TakeInput();
        mutPrint.semSignal(this);
        mutTake.semSignal(this);
        int lowernbr=Integer.parseInt(lower);
        int uppernbr=Integer.parseInt(upper);
        String data="";

        while (lowernbr<=uppernbr)
        {
            data+=lowernbr++ +"\n";
        }
        mutWrite.semWait(this);
        OperatingSystem.writefile("P5.txt", data);
        mutWrite.semSignal(this);

        setProcessState(ProcessState.Terminated);
    }

    public void setProcessState(ProcessState s) {
        status = s;
    }

    public static ProcessState getProcessState(Process p) {
        return p.status;
    }
}

