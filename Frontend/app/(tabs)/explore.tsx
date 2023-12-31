import { ThemeContext } from "@constants/themes";

import { useContext } from "react";
import { View, Pressable } from "react-native";
import MapView, { PROVIDER_GOOGLE } from "react-native-maps";
import { ExplorePageStyles } from "@styles/explore.style";
import { PubCrawlCreateBar } from "@components/pubCrawlCreateBar";
import { Icon } from "@components/icon";
import * as VectorIcons from '@expo/vector-icons';
import { Button } from "@components/buttons";
import { SafeAreaView } from "react-native-safe-area-context";


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


export default function Explore() {
  const theme = useContext(ThemeContext);
  const styles = ExplorePageStyles(theme);
  
  return (
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

      <SafeAreaView pointerEvents="box-none" style={styles.topButtonsContainer}>
        <Button style={styles.button} pressedStyle={styles.buttonPressed}>
          <Icon iconType={VectorIcons.Feather} name='layers'></Icon>
        </Button >
        
        <Button style={styles.button} pressedStyle={styles.buttonPressed}>
          <Icon style={styles.searchIcon} iconType={VectorIcons.Ionicons} name='search'></Icon>
        </Button>
      </SafeAreaView>
     
      <PubCrawlCreateBar />
    </View>
  );
}