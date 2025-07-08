# 소스 코드

```
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
interface FindMax {
    int compare(int a, int b);
}
interface Findmin {
    int compare(int a, int b);
}
interface FindDoublemax{
	double compare(double a, double b);
}
interface FindDoublemin{
	double compare(double a, double b);
}

class People{
	String atdrc;
	int atdrcnm;
	int year;
	int popltnco;
	int hshldco;
	double hshldavrgco;
	int maleco;
	int femaleco;
	public People(String atdrc, int atdrcnm, int year, int popltnco, int hshldco, double avrgco, int maleco, int femaleco) {
		this.atdrc = atdrc;
		this.atdrcnm = atdrcnm;
		this.year = year;
		this.popltnco = popltnco;
		this.hshldco = hshldco;
		this.hshldavrgco = avrgco;
		this.maleco = maleco;
		this.femaleco = femaleco;
		
	}
	public String getName() {
	    return atdrc;
	}
	
	
}

public class Lambdatest {
	public static void main(String[] args) {
		List<People> people = new ArrayList<>();
        String filepath = "C:\\Users\\cyci1234\\Desktop\\과제\\과제\\seoul_people.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
            	if ( line.charAt(0) != '#') {
	                StringTokenizer st = new StringTokenizer(line, ",");
	                if(st.countTokens()==8) {
	                	
	   
		                String atdrc = st.nextToken();
		                int atdrcnm = Integer.parseInt(st.nextToken());
		                int year = Integer.parseInt(st.nextToken());
		                int popltnco = Integer.parseInt(st.nextToken());
		                int hshldco = Integer.parseInt(st.nextToken());
		                double hshldavrgco = Double.parseDouble(st.nextToken());
		                int maleco = Integer.parseInt(st.nextToken());
		                int femaleco = Integer.parseInt(st.nextToken());
		
		                People p = new People(atdrc, atdrcnm, year, popltnco, hshldco, hshldavrgco, maleco, femaleco);
		                people.add(p);
	                }
            	}
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
		FindMax max;
		max = (a, b) -> (a > b) ? a : b;
		
		FindMax min;
		min = (a, b) -> (a < b) ? a : b;
		FindDoublemax dmax;
		dmax = (a, b) -> (a > b) ? a : b;
		FindDoublemin dmin;
		dmin = (a, b) -> (a < b) ? a : b;
		
        int maxmalepeople = people.get(0).maleco;
        int maxfemalepeople = people.get(0).femaleco;
        int minmalepeople = people.get(0).maleco;
        int minfemalepeople = people.get(0).femaleco;
        double maxhshavgeco = people.get(0).hshldavrgco;
        double minhshavgeco = people.get(0).hshldavrgco;
        People maxMaleName = people.get(0);
        People minMaleName = people.get(0);
        People maxFemaleName = people.get(0);
        People minFemaleName = people.get(0);
        People maxAvgName= people.get(0);
        People minAvgName = people.get(0);

        for (People p : people) {
            maxmalepeople = max.compare(maxmalepeople, p.maleco);
            minmalepeople = min.compare(minmalepeople, p.maleco);
            maxfemalepeople = max.compare(maxfemalepeople, p.femaleco);
            minfemalepeople = min.compare(minfemalepeople, p.femaleco);
            maxhshavgeco = dmax.compare(maxhshavgeco, p.hshldavrgco);
            minhshavgeco = dmin.compare(minhshavgeco, p.hshldavrgco);

            if (p.maleco == maxmalepeople) maxMaleName = p;
            if (p.maleco == minmalepeople) minMaleName = p;
            if (p.femaleco == maxfemalepeople) maxFemaleName = p;
            if (p.femaleco == minfemalepeople) minFemaleName = p;
            if (p.hshldavrgco == maxhshavgeco) maxAvgName = p;
            if (p.hshldavrgco == minhshavgeco) minAvgName = p;
        }
        double sumpeople = 0;
        for(int i = 0; i < people.size();i++) {
        	sumpeople += people.get(i).popltnco;
        }
        

        System.out.println("서울의 남자 인구가 가장 많은 구 : " + maxMaleName.getName() + "( " +  maxmalepeople + "명 )");
        System.out.println("서울의 여자 인구가 가장 많은 구 : " + maxFemaleName.getName() + "( " + maxfemalepeople + "명 )");
        System.out.println("서울의 남자 인구가 적은 많은 구 : " + minMaleName.getName() + "( " + minmalepeople + "명 )");
        System.out.println("서울의 여자 인구가 적은 많은 구 : " + minFemaleName.getName() + "( " + minfemalepeople + "명 )");
        System.out.println("서울의 세대당 인구가 가장 많은 구 : " + maxAvgName.getName() + "( " + maxhshavgeco + "명 )");
        System.out.println("서울의 세대당 인구가 가장 적은 구 : " + maxAvgName.getName() + "( " + minhshavgeco + "명 )");
        System.out.println("평균 구의 인구수 : " + (double)(sumpeople/people.size()));
        
	
	}
}
```
