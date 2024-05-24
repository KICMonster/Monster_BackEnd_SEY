import React, { useEffect, useRef } from 'react';
import BasicLayout from '../layouts/BasicLayout';
import "../layouts/BasicLayout.css";
import Grid from '../component/main/Grid';
import '../App.css';


function Home() {
  

  return (
    <BasicLayout>
      <Grid />
    </BasicLayout>
  );
}

export default Home;