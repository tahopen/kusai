package org.kusai.service.olap.totals;

import mondrian.util.Format;

interface FormatList {
  Format getValueFormat(int position, int member);
}