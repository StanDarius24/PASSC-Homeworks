package tema1;

public class ScaunFaraSpatar extends Scaun{

    public ScaunFaraSpatar()
    {
        super();
    }
    @Override
    public boolean isok(Task t)
    {

        String c=t.toString();
        if(super.tasks.contains(c))
            return false;
        else
        if(c.equals("C") && !tasks.contains("C"))
            return true;
        else
        if(c=="F"&& !tasks.contains("F"))
            return tasks.contains("C");
        else
        if(c=="B")
            return false;
        else
        if(c=="S" && !tasks.contains("S"))
            return tasks.contains("F");
        else
        if(c=="P" && !tasks.contains("P"))
            return tasks.contains("C")
                    && tasks.contains("F") && tasks.contains("S");
        else
            return false;
    }
    @Override
    public boolean done()
    {
        return tasks.length()==4;
    }
}
