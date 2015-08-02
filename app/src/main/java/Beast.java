import java.util.ArrayList;

public class Beast {

  public String name;
  public String description;
  public ArrayList<String> weaknesses;

  public void addWeakness(String weakness)
  {
    weaknesses.add(weakness);
  }
  
}
