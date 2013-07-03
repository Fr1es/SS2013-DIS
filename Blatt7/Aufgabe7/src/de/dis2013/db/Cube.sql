select s.TOWN, d.QUARTER, a.PRODUCTFAMILY, cast( sum(f.SOLD) as DOUBLE) as Anz, round(sum( cast(f.TURNOVER as DOUBLE) /1000000)/100, 3) as Verkauft_in_MIO
from vsisp17.fact f 
join vsisp17.DIMSHOP s on (f.SHOPID = s.SHOPID)
join vsisp17.DIMARTICLE a on (f.ARTICLEID = a.ARTICLEID)
join vsisp17.DIMDAY d on (f.DAYID = d.DAYID)
group by cube (s.town, d.quarter, a.PRODUCTFAMILY)
--group by Grouping sets ((s.town, d.quarter, a.PRODUCTFAMILY), (s.town, d.quarter), (s.town, a.PRODUCTFAMILY), (s.town) )
order by s.town, d.quarter, a.PRODUCTFAMILY