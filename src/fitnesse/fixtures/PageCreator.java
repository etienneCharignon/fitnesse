// Copyright (C) 2003-2009 by Object Mentor, Inc. All rights reserved.
// Released under the terms of the CPL Common Public License version 1.0.
package fitnesse.fixtures;

import java.util.StringTokenizer;

import fit.ColumnFixture;
import fitnesse.wiki.*;

public class PageCreator extends ColumnFixture {
  public String pageName;
  public String pageContents;
  public String pageAttributes;

  public boolean valid() throws Exception {
    if (pageContents != null)  {
      pageContents = pageContents.replaceAll("<br>", "\n");
      pageContents = pageContents.replaceAll("<br/>", "\n");
    }
    WikiPage root = FitnesseFixtureContext.context.getRootPage();
    WikiPagePath pagePath = PathParser.parse(pageName);
    WikiPage thePage = WikiPageUtil.addPage(root, pagePath, pageContents);
    if (pageAttributes != null && !pageAttributes.isEmpty()) {
      PageData data = thePage.getData();
      setAttributes(data);
      thePage.commit(data);
      setPageAttributes("");
    }
    return true;
  }

  private void setAttributes(PageData data) throws Exception {
    if (pageAttributes != null) {
      StringTokenizer tokenizer = new StringTokenizer(pageAttributes, ",");
      while (tokenizer.hasMoreTokens()) {
        String nameValuePair = tokenizer.nextToken();
        int equals = nameValuePair.indexOf("=");
        if (equals < 0)
          throw new Exception("Attribute must have form name=value");
        String name = nameValuePair.substring(0, equals);
        String value = nameValuePair.substring(equals + 1);
        data.setAttribute(name, value);
      }
    }
  }

  public void setPageName(String pageName) {
    this.pageName = pageName;
  }

  public void setPageContents(String pageContents) {
    this.pageContents = pageContents;
  }

  public void setPageAttributes(String pageAttributes) {
    this.pageAttributes = pageAttributes;
  }
}
