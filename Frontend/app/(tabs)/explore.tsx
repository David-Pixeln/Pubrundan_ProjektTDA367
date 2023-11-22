<<<<<<< Updated upstream
import { Text } from "react-native";
import { SafeAreaView } from "react-native-safe-area-context";
=======
import { ThemeContext } from "@constants/themes";
import { useContext, useState } from "react";
import { View, Pressable, StyleSheet } from "react-native";
import MapView, { PROVIDER_GOOGLE } from "react-native-maps";
import { ExplorePageStyles } from "@styles/explore.style";
import { PubcrawlCreateBar } from "@components/pubcrawlCreateBar";
import { Icon } from "@components/icon";
import * as VectorIcons from '@expo/vector-icons';



const mapStyle = [
  {
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#2a2d43"
      }
    ]
  },
  {
    "elementType": "labels.icon",
    "stylers": [
      {
        "visibility": "off"
      }
    ]
  },
  {
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#7e8197"
      }
    ]
  },
  {
    "elementType": "labels.text.stroke",
    "stylers": [
      {
        "color": "#2a2d43"
      }
    ]
  },
  {
    "featureType": "administrative",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#7e8197"
      }
    ]
  },
  {
    "featureType": "administrative.country",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#a7aac0"
      }
    ]
  },
  {
    "featureType": "administrative.land_parcel",
    "stylers": [
      {
        "visibility": "off"
      }
    ]
  },
  {
    "featureType": "administrative.locality",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#c6c9df"
      }
    ]
  },
  {
    "featureType": "poi",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#7e8197"
      }
    ]
  },
  {
    "featureType": "poi.park",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#21243a"
      }
    ]
  },
  {
    "featureType": "poi.park",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#6a6d83"
      }
    ]
  },
  {
    "featureType": "poi.park",
    "elementType": "labels.text.stroke",
    "stylers": [
      {
        "color": "#24273d"
      }
    ]
  },
  {
    "featureType": "road",
    "elementType": "geometry.fill",
    "stylers": [
      {
        "color": "#35384e"
      }
    ]
  },
  {
    "featureType": "road",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#9396ac"
      }
    ]
  },
  {
    "featureType": "road.arterial",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#404359"
      }
    ]
  },
  {
    "featureType": "road.highway",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#45485e"
      }
    ]
  },
  {
    "featureType": "road.highway.controlled_access",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#575a70"
      }
    ]
  },
  {
    "featureType": "road.local",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#6a6d83"
      }
    ]
  },
  {
    "featureType": "transit",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#7e8197"
      }
    ]
  },
  {
    "featureType": "water",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#000000"
      }
    ]
  },
  {
    "featureType": "water",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#46495f"
      }
    ]
  }
]
>>>>>>> Stashed changes


export default function Explore() {
  return (
<<<<<<< Updated upstream
    <SafeAreaView>
      <Text>Explore</Text>
    </SafeAreaView>
=======
    <View style={styles.container}>
      <MapView 
        style={styles.map}
        provider={PROVIDER_GOOGLE}
        customMapStyle={mapStyle}
        userInterfaceStyle={theme.dark ? 'dark' : 'light'} 
        showsUserLocation={true} 
        followsUserLocation={true}
        userLocationCalloutEnabled={true}>
      </MapView>
    

      
      <Pressable style={styles.button1}
        onPressIn={handlePressIn}
        onPressOut={handlePressOut}>
        <Icon style={styles.icon} iconType={VectorIcons.Feather} name='layers'></Icon>
      </Pressable >
  
      <Pressable style={styles.button2}>
        <Icon style={styles.searchIcon} iconType={VectorIcons.Ionicons} name='search'></Icon>
      </Pressable>
      
      <PubcrawlCreateBar></PubcrawlCreateBar>
      
    </View>
>>>>>>> Stashed changes
  );
}

const handlePressIn = () => {
  
};

const handlePressOut = () => {
  
  
};

