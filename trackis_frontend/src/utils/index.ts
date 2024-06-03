const fetchData = async (route: string) => {
  const res = await fetch(route);
  const data = await res.json();
  // print data to the console
  console.log(data);
  return data;
};

export { fetchData };
