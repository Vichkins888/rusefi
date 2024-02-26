static void cannedvvtTable1() {
	static const float hardCodedvvtTable1[8][8] = {
{0.000,	0.000,	0.000,	0.000,	0.000,	0.000,	0.000,	0.000,	},
{0.000,	0.000,	0.000,	0.000,	0.000,	0.000,	0.000,	0.000,	},
{0.000,	0.000,	0.000,	0.000,	0.000,	0.000,	0.000,	0.000,	},
{0.000,	0.000,	0.000,	0.000,	0.000,	0.000,	0.000,	0.000,	},
{0.000,	30.000,	35.000,	35.000,	35.000,	30.000,	20.000,	10.000,	},
{0.000,	30.000,	35.000,	35.000,	35.000,	30.000,	20.000,	10.000,	},
{0.000,	30.000,	35.000,	35.000,	35.000,	30.000,	20.000,	10.000,	},
{0.000,	30.000,	35.000,	35.000,	35.000,	30.000,	20.000,	10.000,	},
};
	copyTable(config->vvtTable1, hardCodedvvtTable1);
}

static void cannedvvtTable2() {
	static const float hardCodedvvtTable2[8][8] = {
{50.000,	50.000,	50.000,	50.000,	50.000,	40.000,	40.000,	40.000,	},
{50.000,	50.000,	50.000,	50.000,	50.000,	40.000,	40.000,	40.000,	},
{50.000,	50.000,	50.000,	50.000,	50.000,	40.000,	40.000,	40.000,	},
{50.000,	50.000,	50.000,	50.000,	40.000,	40.000,	40.000,	40.000,	},
{50.000,	50.000,	50.000,	50.000,	40.000,	40.000,	40.000,	40.000,	},
{50.000,	50.000,	50.000,	50.000,	40.000,	40.000,	40.000,	40.000,	},
{50.000,	50.000,	50.000,	50.000,	40.000,	40.000,	40.000,	40.000,	},
{50.000,	50.000,	50.000,	50.000,	40.000,	40.000,	40.000,	40.000,	},
};
	copyTable(config->vvtTable2, hardCodedvvtTable2);
}

static void cannedignitionTable() {
#if (IGN_LOAD_COUNT == DEFAULT_IGN_LOAD_COUNT) && (IGN_RPM_COUNT == DEFAULT_IGN_RPM_COUNT)
	static const float hardCodedignitionTable[16][16] = {
{12.200,	14.200,	18.100,	23.300,	29.900,	36.400,	39.200,	40.000,	-10.000,	-10.000,	-10.000,	-10.000,	-10.000,	-10.000,	-10.000,	-10.000,	},
{12.100,	14.000,	17.800,	22.900,	29.300,	35.600,	39.000,	39.800,	-10.000,	-10.000,	-10.000,	-10.000,	-10.000,	-10.000,	-10.000,	-10.000,	},
{11.800,	13.700,	10.000,	14.900,	28.600,	34.800,	38.700,	39.500,	39.500,	39.500,	39.500,	39.500,	41.000,	41.000,	41.000,	41.000,	},
{11.600,	13.400,	12.300,	17.100,	28.000,	33.900,	38.500,	39.300,	39.300,	39.300,	39.300,	39.300,	40.000,	40.000,	40.000,	40.000,	},
{4.400,	6.200,	9.700,	14.400,	20.300,	26.100,	31.200,	32.000,	32.000,	32.000,	32.000,	32.000,	32.000,	32.000,	32.000,	32.000,	},
{0.200,	1.900,	5.300,	9.800,	11.600,	15.600,	19.100,	20.100,	20.600,	20.700,	20.900,	21.000,	21.100,	21.200,	21.400,	21.600,	},
{-0.100,	1.600,	5.600,	9.200,	7.900,	10.000,	11.900,	13.200,	14.200,	14.500,	14.700,	14.900,	15.200,	15.500,	15.700,	16.200,	},
{-3.900,	-2.200,	1.600,	5.200,	6.200,	6.500,	6.800,	8.300,	9.800,	10.200,	10.600,	10.900,	11.300,	11.700,	12.100,	12.800,	},
{-5.900,	-4.300,	-0.700,	2.600,	3.100,	3.500,	3.800,	5.300,	6.800,	7.200,	7.600,	7.900,	8.300,	8.700,	9.100,	9.800,	},
{-7.800,	-6.500,	-3.100,	0.100,	0.100,	0.600,	0.900,	2.400,	3.900,	4.300,	4.700,	5.000,	5.400,	5.800,	6.200,	6.900,	},
{-9.800,	-8.600,	-5.500,	-2.500,	-3.000,	-2.400,	-2.100,	-0.600,	0.900,	1.300,	1.700,	2.000,	2.400,	2.800,	3.200,	3.900,	},
{-11.800,	-10.700,	-7.900,	-5.000,	-6.100,	-5.400,	-5.100,	-3.600,	-2.100,	-1.700,	-1.300,	-1.000,	-0.600,	-0.200,	0.200,	0.900,	},
{-13.800,	-12.800,	-10.300,	-7.600,	-9.100,	-8.400,	-8.100,	-6.600,	-5.100,	-4.700,	-4.300,	-4.000,	-3.600,	-3.200,	-2.800,	-2.100,	},
{-15.000,	-14.900,	-12.700,	-10.100,	-12.200,	-11.300,	-11.000,	-9.500,	-8.000,	-7.600,	-7.200,	-6.900,	-6.500,	-6.100,	-5.700,	-5.000,	},
{-15.000,	-15.000,	-15.000,	-12.700,	-15.000,	-14.300,	-14.000,	-12.500,	-11.000,	-10.600,	-10.200,	-9.900,	-9.500,	-9.100,	-8.700,	-8.000,	},
{-15.000,	-15.000,	-15.000,	-15.000,	-15.000,	-15.000,	-15.000,	-15.000,	-14.000,	-13.600,	-13.200,	-12.900,	-12.500,	-12.100,	-11.700,	-11.000,	},
};
	copyTable(config->ignitionTable, hardCodedignitionTable);
#endif
}

static void cannedveTable() {
	static const float hardCodedveTable[16][16] = {
{70.300,	70.300,	70.400,	70.500,	70.600,	70.600,	70.300,	29.900,	29.800,	29.900,	30.400,	31.400,	33.000,	35.100,	37.700,	40.900,	},
{70.800,	71.500,	72.100,	72.900,	73.300,	72.800,	72.000,	31.300,	31.200,	31.500,	32.200,	33.500,	35.300,	37.700,	40.500,	43.900,	},
{71.700,	72.900,	74.000,	74.900,	74.900,	74.000,	73.000,	82.300,	82.100,	82.400,	83.200,	84.500,	86.300,	88.700,	91.600,	95.000,	},
{75.100,	77.400,	78.500,	78.600,	77.700,	76.200,	75.000,	84.100,	84.000,	84.200,	85.100,	86.400,	88.300,	90.700,	93.600,	97.100,	},
{80.400,	82.100,	82.300,	81.500,	79.900,	78.200,	76.900,	86.400,	86.200,	86.500,	87.400,	88.700,	90.600,	93.000,	96.000,	99.500,	},
{85.000,	85.500,	85.000,	83.700,	82.000,	80.100,	78.700,	88.600,	88.500,	88.700,	89.600,	91.000,	92.900,	95.300,	98.400,	101.900,	},
{88.500,	88.200,	87.300,	85.800,	83.900,	82.000,	80.600,	90.900,	90.700,	91.000,	91.900,	93.200,	95.200,	97.700,	100.700,	104.300,	},
{86.200,	85.400,	84.300,	82.700,	80.800,	78.900,	77.600,	93.100,	93.000,	93.200,	94.100,	95.500,	97.500,	100.000,	103.100,	106.700,	},
{105.600,	104.600,	103.400,	101.700,	99.800,	97.800,	96.400,	95.400,	95.200,	95.500,	96.400,	97.800,	99.800,	102.300,	105.500,	109.100,	},
{113.400,	112.100,	110.800,	109.000,	106.900,	104.800,	103.300,	102.300,	102.100,	102.400,	103.300,	104.800,	106.900,	109.600,	112.900,	116.800,	},
{121.400,	120.000,	118.600,	116.600,	114.400,	112.200,	110.600,	109.500,	109.300,	109.600,	110.600,	112.200,	114.400,	117.300,	120.800,	124.900,	},
{127.600,	126.500,	125.100,	123.000,	120.700,	118.300,	116.600,	115.500,	115.300,	115.600,	116.600,	118.300,	120.700,	123.700,	127.400,	127.600,	},
{127.600,	127.600,	127.600,	127.600,	125.700,	123.300,	121.500,	120.400,	120.100,	120.500,	121.500,	123.300,	125.700,	127.600,	127.600,	127.600,	},
{127.600,	127.600,	127.600,	127.600,	127.600,	127.000,	125.200,	124.000,	123.800,	124.200,	125.200,	127.000,	127.600,	127.600,	127.600,	127.600,	},
{127.600,	127.600,	127.600,	127.600,	127.600,	127.600,	127.600,	126.500,	126.300,	126.700,	127.600,	127.600,	127.600,	127.600,	127.600,	127.600,	},
{127.600,	127.600,	127.600,	127.600,	127.600,	127.600,	127.600,	127.600,	127.600,	127.600,	127.600,	127.600,	127.600,	127.600,	127.600,	127.600,	},
};
	copyTable(config->veTable, hardCodedveTable);
}

static void cannedinjectionPhase() {
	static const float hardCodedinjectionPhase[16][16] = {
{-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-250.000,	-250.000,	-250.000,	-240.000,	-240.000,	-240.000,	-240.000,	-240.000,	},
{-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-250.000,	-250.000,	-250.000,	-240.000,	-240.000,	-240.000,	-240.000,	-240.000,	},
{-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-250.000,	-250.000,	-250.000,	-240.000,	-240.000,	-240.000,	-240.000,	-240.000,	},
{-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-250.000,	-250.000,	-250.000,	-240.000,	-240.000,	-240.000,	-240.000,	-240.000,	},
{-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-250.000,	-250.000,	-250.000,	-240.000,	-240.000,	-240.000,	-240.000,	-240.000,	},
{-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-250.000,	-250.000,	-230.000,	-200.000,	-200.000,	-200.000,	-200.000,	-200.000,	},
{-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-250.000,	-250.000,	-230.000,	-200.000,	-200.000,	-200.000,	-200.000,	-200.000,	},
{-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-250.000,	-250.000,	-230.000,	-200.000,	-200.000,	-200.000,	-200.000,	-200.000,	},
{-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-250.000,	-250.000,	-230.000,	-200.000,	-200.000,	-200.000,	-200.000,	-200.000,	},
{-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-250.000,	-250.000,	-230.000,	-200.000,	-200.000,	-200.000,	-200.000,	-200.000,	},
{-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-250.000,	-250.000,	-230.000,	-200.000,	-200.000,	-200.000,	-200.000,	-200.000,	},
{-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-250.000,	-250.000,	-230.000,	-200.000,	-200.000,	-200.000,	-200.000,	-200.000,	},
{-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-250.000,	-250.000,	-230.000,	-200.000,	-200.000,	-200.000,	-200.000,	-200.000,	},
{-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-250.000,	-250.000,	-230.000,	-180.000,	-180.000,	-180.000,	-180.000,	-180.000,	},
{-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-250.000,	-250.000,	-230.000,	-180.000,	-180.000,	-180.000,	-180.000,	-180.000,	},
{-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-220.000,	-250.000,	-250.000,	-230.000,	-180.000,	-180.000,	-180.000,	-180.000,	-180.000,	},
};
	copyTable(config->injectionPhase, hardCodedinjectionPhase);
}

static void cannedlambdaTable() {
	static const float hardCodedlambdaTable[16][16] = {
{14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	},
{14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	},
{14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	},
{14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	},
{14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	14.700,	},
{14.000,	14.000,	14.000,	14.000,	14.000,	14.000,	14.000,	14.000,	14.000,	14.000,	14.000,	14.000,	14.000,	14.000,	14.000,	14.000,	},
{13.500,	13.500,	13.500,	13.500,	13.500,	13.500,	13.500,	13.500,	13.500,	13.500,	13.500,	13.500,	13.500,	13.500,	13.500,	13.500,	},
{13.200,	13.200,	13.200,	13.200,	13.200,	13.200,	13.200,	13.200,	13.200,	13.200,	13.200,	13.200,	13.200,	13.200,	13.200,	13.200,	},
{13.100,	13.100,	13.100,	13.100,	13.100,	13.100,	13.100,	13.100,	13.100,	13.100,	13.100,	13.100,	13.100,	13.100,	13.100,	13.100,	},
{12.900,	12.900,	12.900,	12.900,	12.900,	12.900,	12.900,	12.900,	12.900,	12.900,	12.900,	12.900,	12.900,	12.900,	12.900,	12.900,	},
{12.600,	12.600,	12.600,	12.600,	12.600,	12.600,	12.600,	12.600,	12.600,	12.600,	12.600,	12.600,	12.600,	12.600,	12.600,	12.600,	},
{12.300,	12.300,	12.300,	12.300,	12.300,	12.300,	12.300,	12.300,	12.300,	12.300,	12.300,	12.300,	12.300,	12.300,	12.300,	12.300,	},
{11.800,	11.800,	11.800,	11.800,	11.800,	11.800,	11.800,	11.800,	11.800,	11.800,	11.800,	11.800,	11.800,	11.800,	11.800,	11.800,	},
{11.300,	11.300,	11.300,	11.300,	11.300,	11.300,	11.300,	11.300,	11.300,	11.300,	11.300,	11.300,	11.300,	11.300,	11.300,	11.300,	},
{11.000,	11.000,	11.000,	11.000,	11.000,	11.000,	11.000,	11.000,	11.000,	11.000,	11.000,	11.000,	11.000,	11.000,	11.000,	11.000,	},
{10.700,	10.700,	10.700,	10.700,	10.700,	10.700,	10.700,	10.700,	10.700,	10.700,	10.700,	10.700,	10.700,	10.700,	10.700,	10.700,	},
};
	copyTable(config->lambdaTable, hardCodedlambdaTable);
}
