package cn.uibe.LB.myAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ReconstructTokenizeResult {
	
	//首先将每个词语封装为word对象
	 Word Index = new Word();
	  public  String[] Reconstructsinglecorpus(Vector corpus){
          int j=0;
          List lstofword = new ArrayList<Word>();
          //循环将每一个单元格的内容封装为Word对象
          while (j <corpus.size())
          {
              Word word = new Word();
              word.position = j;
              word.content=GetCorpusWithoutAnnotations.getpurestring((String)corpus.get(j));
              word.annotations = getAnnotations((String)corpus.get(j));
              word.flag=BaseNPnjudge(word.annotations);
              lstofword.add(word);
              j++;
          }

          Index = (Word)lstofword.get(0);
          //递归重构BaseNP
          for (int i = 0; i < lstofword.size(); )
          {
              if (((Word)lstofword.get(i)).flag == 1)
              {
                  if (getBaseNP((Word)lstofword.get(i),  lstofword))
                  {
                      i = Index.position;
                  }
                  else
                  {
                      i++;
                  }
              }
              else {
                  i++;
              }
          }

          String[] arrayofword=new String[lstofword.size()];
          for (int i = 0; i < lstofword.size(); i++) {
              arrayofword[i]=((Word)lstofword.get(i)).content;
          }
          return arrayofword;
      
      }

      private int BaseNPnjudge(String str) {
      
       //获取后面的词性标注，如果是名词性的，则其符合标注内容，
                  if (str.indexOf("n") == 0) 
                      return 1;
                  else 
                      return 0;
       
      }

      private Boolean BaseNPdeJudge(String str) {
          if (str.indexOf("ude") == 0)
              return true;
          else
              return false;
      }

      private Boolean BaseNPvvJudge(String str) {
          if (str.indexOf("v") == 0 && str != "vyou" && str != "vshi") 
              return true;
              else
              return false;
      }

      private Boolean BaseNPvaJudge(String str)
      {
          if (str.indexOf("a") == 0)
              return true;
          else
              return false;
      }





      private String getAnnotations(String original)
      {
          if (original == "")
          {
              return "";
          }
          else
          {
              int lastindex = original.lastIndexOf('/');
              if (lastindex < 0)
              {
                  return "";
              }
              else
              {
                  String temp = original.substring(lastindex + 1, original.length() - lastindex - 1);
                  return temp;
              }
          }
         
             }

      private Boolean getBaseNP(Word BaseNP,  List<Word> lstofword)
      {//记录分词结果是否经过重构
    	  Boolean ChangedFlag = false;
          List<Integer> removedposition = new ArrayList<Integer>();
          int position=0;
          //BaseNP+BaseNP
          if (lstofword.get(BaseNP.position + 1).flag == 1)
          {
              position = BaseNP.position;
              lstofword.get(position).content = lstofword.get(position).content + lstofword.get(position+1).content;
              removedposition.add(position + 1);
              changeposition( lstofword, removedposition);
              lstofword.remove(lstofword.get(position + 1));
              //记录下一个需要重构的位置
              Index = lstofword.get(position + 1);
              ChangedFlag = true;
              getBaseNP(lstofword.get(position),  lstofword);
              return ChangedFlag;
          }
          else if (BaseNP.position - 1 >= 0 && lstofword.get(BaseNP.position - 1).flag == 1)
          {
              position = BaseNP.position;
              lstofword.get(position - 1).content = lstofword.get(position - 1).content + lstofword.get(position).content;
              removedposition.add(position);
              changeposition(lstofword, removedposition);
              lstofword.remove(lstofword.get(position));
              Index = lstofword.get(position);
              ChangedFlag = true;
              //变更lstofword中被删除项之后的item的position属性
              getBaseNP(lstofword.get(position - 1),  lstofword);
              return ChangedFlag;
          }
          //BaseNP+de+BaseNP
          else if (BaseNPdeJudge(lstofword.get(BaseNP.position + 1).annotations) && lstofword.get(BaseNP.position + 2).flag == 1)
          {
              position = BaseNP.position;
              lstofword.get(position).content = lstofword.get(position).content + lstofword.get(position + 1).content + lstofword.get(position + 2);
              removedposition.add(position + 1);
              removedposition.add(position + 2);
              changeposition( lstofword, removedposition);
              lstofword.remove(lstofword.get(position + 1));
              lstofword.remove(lstofword.get(position + 1));
              Index = lstofword.get(position + 1);
              ChangedFlag = true;
              getBaseNP(lstofword.get(position),  lstofword);
              return ChangedFlag;
          }
          else if (BaseNP.position - 2 >= 0 && BaseNPdeJudge(lstofword.get(BaseNP.position - 1).annotations) && lstofword.get(BaseNP.position - 2).flag == 1)
          {
              position = BaseNP.position;
              lstofword.get(position - 2).content = lstofword.get(position - 2).content + lstofword.get(position - 1).content + lstofword.get(position).content;
              removedposition.add(position - 1);
              removedposition.add(position);
              changeposition( lstofword, removedposition);
              lstofword.remove(lstofword.get(position - 1));
              lstofword.remove(lstofword.get(position-1));
              Index = lstofword.get(position - 1);
              ChangedFlag = true;
              getBaseNP(lstofword.get(position - 2),  lstofword);
              return ChangedFlag;
          }
          //v+a+de+BaseNP
          else if (BaseNP.position - 3 >= 0 && BaseNPvvJudge(lstofword.get(BaseNP.position - 3).annotations) && BaseNPvaJudge(lstofword.get(BaseNP.position - 2).annotations) && BaseNPdeJudge(lstofword.get(BaseNP.position - 1).annotations))
          {
              position = BaseNP.position;
              lstofword.get(position - 3).content = lstofword.get(position - 3).content + lstofword.get(position - 2).content + lstofword.get(position - 1).content +lstofword.get(position).content;
              removedposition.add(position);
              removedposition.add(position - 2);
              removedposition.add(position - 1);
              changeposition( lstofword, removedposition);
              lstofword.remove(lstofword.get(position));
              lstofword.remove(lstofword.get(position - 2));
              lstofword.remove(lstofword.get(position - 2));
              Index = lstofword.get(position - 2);
              ChangedFlag = true;
              getBaseNP(lstofword.get(position - 3),  lstofword);
              return ChangedFlag;
          }
          //v+BaseNP
          else if (BaseNP.position - 1 >= 0 && BaseNPvvJudge(lstofword.get(BaseNP.position - 1).annotations))
          {
              position = BaseNP.position;
              lstofword.get(position - 1).content =  lstofword.get(position - 1).content + lstofword.get(position).content;
              removedposition.add(position);
              changeposition( lstofword, removedposition);
              lstofword.remove(lstofword.get(position));
              Index = lstofword.get(position);
              ChangedFlag = true;
              getBaseNP( lstofword.get(position - 1),  lstofword);
              return ChangedFlag;
          }
          else
          {
              return ChangedFlag;
          }

      }
 

      private void changeposition( List<Word> lstofword, List<Integer> removedposition) {
          for (int i = 0; i < removedposition.size(); i++) {
              for (int j = removedposition.get(i)+1; j < lstofword.size(); j++) {
                  lstofword.get(j).position = lstofword.get(j).position - 1;
              }
          }
      }
      
}


